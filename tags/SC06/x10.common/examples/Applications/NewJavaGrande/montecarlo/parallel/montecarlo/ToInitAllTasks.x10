package montecarlo;

/**
 * X10 port of montecarlo benchmark from Section 2 of Java Grande Forum Benchmark Suite (Version 2.0).
 *
 * @author Vivek Sarkar (vsarkar@us.ibm.com)
 */
public value ToInitAllTasks {
	final private String header;
	final private String name;
	final private int startDate;
	final private int endDate;
	final private double dTime;
	final private int returnDefinition;
	final private double expectedReturnRate;
	final private double volatility;
	final private int nTimeSteps;
	final private double pathStartValue;

	/**
	 * Constructor, for initialisation data which are common to all
	 * computation tasks.
	 *
	 * @param header Simple header string.
	 * @param name The name of the security which this Monte Carlo path
	 *             should represent.
	 * @param startDate The date when the path starts, in 'YYYYMMDD' format.
	 * @param endDate The date when the path ends, in 'YYYYMMDD' format.
	 * @param dTime The interval in the data between successive data points
	 *              in the generated path.
	 * @param returnDefinition How the statistic variables were defined,
	 *                         according to the definitions in
	 *                         <code>ReturnPath</code>'s two class variables
	 *                         <code>COMPOUNDED</code> and
	 *                         <code>NONCOMPOUNDED</code>.
	 * @param expectedReturnRate The measured expected return rate for which
	 *       to generate.
	 * @param volatility The measured volatility for which to generate.
	 * @param nTimeSteps The number of time steps for which to generate.
	 * @param pathStartValue The stock price value to use at the start of each
	 *        Monte Carlo simulation path.
	 */
	public ToInitAllTasks(String header_, String name_, int startDate_, int endDate_,
						  double dTime_, int returnDefinition_,
						  double expectedReturnRate_, double volatility_,
						  double pathStartValue_)
	{
		header             = header_;
		name               = name_;
		startDate          = startDate_;
		endDate            = endDate_;
		dTime              = dTime_;
		returnDefinition   = returnDefinition_;
		expectedReturnRate = expectedReturnRate_;
		volatility         = volatility_;
		nTimeSteps         = 0; // vj
		pathStartValue     = pathStartValue_;
	}

	/**
	 * Another constructor, slightly easier to use by having slightly
	 * fewer arguments.  Makes use of the "ReturnPath" object to
	 * accomplish this.
	 *
	 * @param obj Object used to define the instance variables which
	 *            should be carried over to this object.
	 * @param nTimeSteps The number of time steps which the Monte
	 *                   Carlo generator should make.
	 * @param pathStartValue The stock price value to use at the start of each
	 *        Monte Carlo simulation path.
	 * @exception DemoException thrown if there is a problem accessing the
	 *                          instance variables from the target objetct.
	 */
	public ToInitAllTasks(ReturnPath obj, int nTimeSteps_, double pathStartValue_)
		throws DemoException
	{
		//
		// Instance variables defined in the PathId object.
		header = ""; //vj
		name      = obj.get_name();
		startDate = obj.get_startDate();
		endDate   = obj.get_endDate();
		dTime     = obj.get_dTime();
		//
		// Instance variables defined in ReturnPath object.
		returnDefinition   = obj.get_returnDefinition();
		expectedReturnRate = obj.get_expectedReturnRate();
		volatility         = obj.get_volatility();
		nTimeSteps         = nTimeSteps_;
		pathStartValue     = pathStartValue_;
	}

	//------------------------------------------------------------------------
	// Accessor methods for class ToInitAllTasks.
	// Generated by 'makeJavaAccessor.pl' script.  HWY.  20th January 1999.
	//------------------------------------------------------------------------

	/**
	 * Accessor method for private instance variable <code>header</code>.
	 * @return Value of instance variable <code>header</code>.
	 */
	public String get_header() {
		return (this.header);
	}

	/**
	 * Set method for private instance variable <code>header</code>.
	 * @param header the value to set for the instance variable <code>header</code>.
	 */
	/*
	public void set_header(String header) {
		this.header = header;
	}
	*/

	/**
	 * Accessor method for private instance variable <code>name</code>.
	 * @return Value of instance variable <code>name</code>.
	 */
	public String get_name() {
		return (this.name);
	}

	/**
	 * Set method for private instance variable <code>name</code>.
	 * @param name the value to set for the instance variable <code>name</code>.
	 */
	/*
	public void set_name(String name) {
		this.name = name;
	}
	*/

	/**
	 * Accessor method for private instance variable <code>startDate</code>.
	 * @return Value of instance variable <code>startDate</code>.
	 */
	public int get_startDate() {
		return (this.startDate);
	}

	/**
	 * Set method for private instance variable <code>startDate</code>.
	 * @param startDate the value to set for the instance variable <code>startDate</code>.
	 */
	/*
	public void set_startDate(int startDate) {
		this.startDate = startDate;
	}
	*/

	/**
	 * Accessor method for private instance variable <code>endDate</code>.
	 * @return Value of instance variable <code>endDate</code>.
	 */
	public int get_endDate() {
		return (this.endDate);
	}

	/**
	 * Set method for private instance variable <code>endDate</code>.
	 * @param endDate the value to set for the instance variable <code>endDate</code>.
	 */
	/*
	public void set_endDate(int endDate) {
		this.endDate = endDate;
	}
	*/

	/**
	 * Accessor method for private instance variable <code>dTime</code>.
	 * @return Value of instance variable <code>dTime</code>.
	 */
	public double get_dTime() {
		return (this.dTime);
	}

	/**
	 * Set method for private instance variable <code>dTime</code>.
	 * @param dTime the value to set for the instance variable <code>dTime</code>.
	 */
	/*
	public void set_dTime(double dTime) {
		this.dTime = dTime;
	}
	*/

	/**
	 * Accessor method for private instance variable <code>returnDefinition</code>.
	 * @return Value of instance variable <code>returnDefinition</code>.
	 */
	public int get_returnDefinition() {
		return (this.returnDefinition);
	}

	/**
	 * Set method for private instance variable <code>returnDefinition</code>.
	 * @param returnDefinition the value to set for the instance variable <code>returnDefinition</code>.
	 */
	/*
	public void set_returnDefinition(int returnDefinition) {
		this.returnDefinition = returnDefinition;
	}
	*/

	/**
	 * Accessor method for private instance variable <code>expectedReturnRate</code>.
	 * @return Value of instance variable <code>expectedReturnRate</code>.
	 */
	public double get_expectedReturnRate() {
		return (this.expectedReturnRate);
	}

	/**
	 * Set method for private instance variable <code>expectedReturnRate</code>.
	 * @param expectedReturnRate the value to set for the instance variable <code>expectedReturnRate</code>.
	 */
	/*
	public void set_expectedReturnRate(double expectedReturnRate) {
		this.expectedReturnRate = expectedReturnRate;
	}
	*/

	/**
	 * Accessor method for private instance variable <code>volatility</code>.
	 * @return Value of instance variable <code>volatility</code>.
	 */
	public double get_volatility() {
		return (this.volatility);
	}

	/**
	 * Set method for private instance variable <code>volatility</code>.
	 * @param volatility the value to set for the instance variable <code>volatility</code>.
	 */
	/*
	public void set_volatility(double volatility) {
		this.volatility = volatility;
	}
	*/

	/**
	 * Accessor method for private instance variable <code>nTimeSteps</code>.
	 * @return Value of instance variable <code>nTimeSteps</code>.
	 */
	public int get_nTimeSteps() {
		return (this.nTimeSteps);
	}

	/**
	 * Set method for private instance variable <code>nTimeSteps</code>.
	 * @param nTimeSteps the value to set for the instance variable <code>nTimeSteps</code>.
	 */
	/*
	public void set_nTimeSteps(int nTimeSteps) {
		this.nTimeSteps = nTimeSteps;
	}
	*/

	/**
	 * Accessor method for private instance variable <code>pathStartValue</code>.
	 * @return Value of instance variable <code>pathStartValue</code>.
	 */
	public double get_pathStartValue() {
		return (this.pathStartValue);
	}

	/**
	 * Set method for private instance variable <code>pathStartValue</code>.
	 * @param pathStartValue the value to set for the instance variable <code>pathStartValue</code>.
	 */
	/*
	public void set_pathStartValue(double pathStartValue) {
		this.pathStartValue = pathStartValue;
	}
	*/

	//------------------------------------------------------------------------
}

