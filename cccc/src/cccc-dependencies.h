/*
 * CCCC analysis toy, dependency-analysis functions.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * Copyright (c) 2007 Paul E. McKenney and Vijay Saraswat, IBM Corporation.
 */

/*
 * Infer write-to-read dependencies by matching the value read to
 * the unique write statement producing that value.  If multiple
 * write statements produce a given value, no inference is possible
 * for reads of that value for that variable.  Check to make
 * sure that each read depends on at most one write.  Reads with
 * no write dependencies must read the value zero.
 */
void infer_read_dependencies(int lineno)
{
	int ndeps;
	int task;
	int stmt;
	int rval;
	int val;
	int valcnt[MAX_VARS][MAX_VALS] = { 0 };
	int valtask[MAX_VARS][MAX_VALS];
	int valstmt[MAX_VARS][MAX_VALS];
	int var;

	/* Account for each variable's initial value of zero. */

	for_each_variable(var) {
		valcnt[var][0] = 1;
	}

	/* Count the number of writes producing each value for each variable. */

	for_each_statement_does_write(task, stmt) {
		var = task_vars[task][stmt];
		val = task_wvals[task][stmt];
		valcnt[var][val]++;
		valtask[var][val] = task;
		valstmt[var][val] = stmt;
	}

	/* Infer dependencies for reads. */

	for_each_statement_does_read(task, stmt) {
		var = task_vars[task][stmt];
		rval = task_rvals[task][stmt];
		if ((rval > 0) &&
		    (valcnt[var][rval] == 1)) {
			if (statement_depends_on(dep_map, task, stmt) == 0) {
				read_map[valtask[var][rval]]
					[valstmt[var][rval]]
					[task][stmt] = 1;
			}
		}
	}

	/* Scan read dependencies for sanity. */

	for_each_statement_does_read(task, stmt) {
		ndeps = statement_depends_on(dep_map, task, stmt) +
			statement_depends_on(read_map, task, stmt);
		if ((task_rvals[task][stmt] > 0) &&
		    (ndeps != 1)) {
		     	char buf[100];

			sprintf(buf,
				"Statement %d.%d depends on %d statements!\n",
				task, stmt, ndeps);
			warn(lineno, buf);
		}
	}
}

/*
 * Check to make sure that write dependencies are full linear chains.
 * This checking assumes that the user has eliminated all warnings!
 */
void check_write_dependencies(int lineno)
{
	int var;
	int ft;
	int fs;
	int tt;
	int ts;
	int ndep[MAX_VARS] = { 0 };
	int nwrite[MAX_VARS] = { 0 };
	char buf[100];

	/*
	 * Sum up per-variable write-write dependencies, both specified
	 * and inferred.
	 */

	for_each_dependency(dep_map, ft, fs, tt, ts) {
		if (statement_does_write(ft, fs) &&
		    statement_does_write(tt, ts) &&
		    (task_vars[ft][fs] == task_vars[tt][ts])) {
			ndep[task_vars[ft][fs]]++;
		}
	}
	for_each_dependency(read_map, ft, fs, tt, ts) {
		if (statement_does_write(ft, fs) &&
		    statement_does_write(tt, ts) &&
		    (task_vars[ft][fs] == task_vars[tt][ts])) {
			ndep[task_vars[ft][fs]]++;
		}
	}

	/* Sum up per-variable write statements. */

	for_each_statement(ft, fs) {
		if (statement_does_write(ft, fs)) {
			nwrite[task_vars[ft][fs]]++;
		}
	}

	/* If no warnings, one more statement that dependencies. */

	for_each_variable(var) {
		if ((nwrite[var] != 0) &&
		    (ndep[var] != nwrite[var] - 1)) {
			sprintf(buf,
				"Variable %c has non-linear write chain",
				var + 'a');
			warn(lineno, buf);
		}
	}
}

/*
 * Build all the program-order dependencies into po_map.
 * Later need to pay attention only to memory barriers @@@ .
 */
void build_program_order(void)
{
	int t;
	int fs;
	int ts;

	for_each_task(t) {
		for_each_statement_pair_in_task(t, fs, ts) {
			po_map[t][fs][t][ts] = 1;
		}
	}
}

/*
 * Update prop_map, and return 1 if this was a real change.
 */
int add_prop(ft, fs, tt, ts)
{
	if (prop_map[ft][fs][tt][ts]) {
		return 0;
	}
	prop_map[ft][fs][tt][ts] = 1;
	return 1;
}

/*
 * Do one cycle of the CCCC "propagation" of dependencies from and to
 * prop_map.
 * This function currently implicitly assumes each task's statements
 * run in program order, which will need to be reworked when explicit
 * memory barriers are added.  @@@
 */
int propagate_dependencies_help(void)
{
	int s1;
	int s2a;
	int s2b;
	int t1;
	int t2;
	int pc = 0;

	for_each_task(t2) {
		for_each_statement_pair_in_task(t2, s2a, s2b) {
			for_each_other_task(t2, t1) {
				for_each_statement_in_task(t1, s1) {
					if (dep_map[t1][s1][t2][s2a] ||
					    read_map[t1][s1][t2][s2a] ||
					    prop_map[t1][s1][t2][s2a]) {
						prop_map[t1][s1][t2][s2b] = 1;
						pc += add_prop(t1, s1, t2, s2b);
					}
					if (dep_map[t2][s2b][t1][s1] ||
					    read_map[t2][s2b][t1][s1] ||
					    prop_map[t2][s2b][t1][s1]) {
						prop_map[t2][s2a][t1][s1] = 1;
						pc += add_prop(t2, s2a, t1, s1);
					}
				}
			}
		}
	}
	return pc;
}

void propagate_dependencies(void)
{
	if (propagate_dependencies_help()) {
		while (propagate_dependencies_help()) {
			fprintf(stderr,
				"propagate_dependencies_help() repeatedly!\n");
		}
	}
}

/*
 * Merge the first dependency map onto the second.
 */
void merge_dependency(dependency_map_t depsrc, dependency_map_t depdst)
{
	int ft;
	int fs;
	int tt;
	int ts;

	for_each_dependency(depsrc, ft, fs, tt, ts) {
		depdst[ft][fs][tt][ts] = 1;
	}
}
