
//#line 18 "/Users/nystrom/work/x10/1.7/x10.compiler.p3/src/x10/parser/x10.g"
//
// Licensed Material
// (C) Copyright IBM Corp, 2006
//

package x10.parser;

public interface X10Parsersym {
    public final static int
      TK_IntegerLiteral = 79,
      TK_LongLiteral = 80,
      TK_FloatingPointLiteral = 81,
      TK_DoubleLiteral = 82,
      TK_CharacterLiteral = 83,
      TK_StringLiteral = 84,
      TK_MINUS_MINUS = 86,
      TK_OR = 112,
      TK_MINUS = 92,
      TK_MINUS_EQUAL = 124,
      TK_NOT = 94,
      TK_NOT_EQUAL = 113,
      TK_REMAINDER = 117,
      TK_REMAINDER_EQUAL = 125,
      TK_AND = 114,
      TK_AND_AND = 118,
      TK_AND_EQUAL = 126,
      TK_LPAREN = 1,
      TK_RPAREN = 75,
      TK_MULTIPLY = 115,
      TK_MULTIPLY_EQUAL = 127,
      TK_COMMA = 85,
      TK_DOT = 90,
      TK_DIVIDE = 119,
      TK_DIVIDE_EQUAL = 128,
      TK_COLON = 95,
      TK_SEMICOLON = 76,
      TK_QUESTION = 129,
      TK_AT = 2,
      TK_LBRACKET = 74,
      TK_RBRACKET = 104,
      TK_XOR = 116,
      TK_XOR_EQUAL = 130,
      TK_LBRACE = 91,
      TK_OR_OR = 131,
      TK_OR_EQUAL = 132,
      TK_RBRACE = 99,
      TK_TWIDDLE = 96,
      TK_PLUS = 93,
      TK_PLUS_PLUS = 87,
      TK_PLUS_EQUAL = 133,
      TK_LESS = 108,
      TK_LEFT_SHIFT = 120,
      TK_LEFT_SHIFT_EQUAL = 134,
      TK_RIGHT_SHIFT = 121,
      TK_RIGHT_SHIFT_EQUAL = 135,
      TK_UNSIGNED_RIGHT_SHIFT = 122,
      TK_UNSIGNED_RIGHT_SHIFT_EQUAL = 136,
      TK_LESS_EQUAL = 109,
      TK_EQUAL = 97,
      TK_EQUAL_EQUAL = 101,
      TK_GREATER = 110,
      TK_GREATER_EQUAL = 111,
      TK_ELLIPSIS = 141,
      TK_RANGE = 138,
      TK_ARROW = 88,
      TK_DARROW = 123,
      TK_SUBTYPE = 105,
      TK_SUPERTYPE = 106,
      TK_abstract = 7,
      TK_as = 33,
      TK_at = 49,
      TK_assert = 50,
      TK_async = 51,
      TK_ateach = 41,
      TK_atomic = 23,
      TK_await = 52,
      TK_break = 53,
      TK_case = 29,
      TK_catch = 34,
      TK_class = 14,
      TK_clocked = 35,
      TK_const = 17,
      TK_continue = 54,
      TK_def = 18,
      TK_default = 30,
      TK_do = 42,
      TK_else = 43,
      TK_extends = 36,
      TK_extern = 24,
      TK_false = 55,
      TK_final = 8,
      TK_finally = 37,
      TK_finish = 56,
      TK_for = 44,
      TK_foreach = 45,
      TK_future = 100,
      TK_in = 107,
      TK_goto = 57,
      TK_has = 58,
      TK_here = 59,
      TK_if = 60,
      TK_implements = 46,
      TK_import = 38,
      TK_incomplete = 25,
      TK_instanceof = 31,
      TK_interface = 11,
      TK_local = 26,
      TK_native = 19,
      TK_new = 61,
      TK_next = 62,
      TK_nonblocking = 27,
      TK_now = 63,
      TK_null = 64,
      TK_or = 47,
      TK_package = 39,
      TK_private = 3,
      TK_property = 22,
      TK_protected = 4,
      TK_public = 5,
      TK_return = 65,
      TK_safe = 12,
      TK_self = 66,
      TK_sequential = 28,
      TK_shared = 32,
      TK_static = 6,
      TK_strictfp = 10,
      TK_super = 78,
      TK_switch = 67,
      TK_synchronized = 142,
      TK_this = 77,
      TK_throw = 68,
      TK_throws = 48,
      TK_transient = 15,
      TK_true = 69,
      TK_try = 70,
      TK_type = 9,
      TK_unsafe = 71,
      TK_val = 20,
      TK_value = 13,
      TK_var = 21,
      TK_volatile = 16,
      TK_when = 72,
      TK_while = 40,
      TK_EOF_TOKEN = 137,
      TK_IDENTIFIER = 73,
      TK_SlComment = 143,
      TK_MlComment = 144,
      TK_DocComment = 145,
      TK_ErrorId = 102,
      TK_PathType = 98,
      TK_any = 139,
      TK_current = 140,
      TK_SynchronizedStatement = 103,
      TK_ObjectKind = 146,
      TK_ArrayInitailizer = 89,
      TK_ERROR_TOKEN = 147;

    public final static String orderedTerminalSymbols[] = {
                 "",
                 "LPAREN",
                 "AT",
                 "private",
                 "protected",
                 "public",
                 "static",
                 "abstract",
                 "final",
                 "type",
                 "strictfp",
                 "interface",
                 "safe",
                 "value",
                 "class",
                 "transient",
                 "volatile",
                 "const",
                 "def",
                 "native",
                 "val",
                 "var",
                 "property",
                 "atomic",
                 "extern",
                 "incomplete",
                 "local",
                 "nonblocking",
                 "sequential",
                 "case",
                 "default",
                 "instanceof",
                 "shared",
                 "as",
                 "catch",
                 "clocked",
                 "extends",
                 "finally",
                 "import",
                 "package",
                 "while",
                 "ateach",
                 "do",
                 "else",
                 "for",
                 "foreach",
                 "implements",
                 "or",
                 "throws",
                 "at",
                 "assert",
                 "async",
                 "await",
                 "break",
                 "continue",
                 "false",
                 "finish",
                 "goto",
                 "has",
                 "here",
                 "if",
                 "new",
                 "next",
                 "now",
                 "null",
                 "return",
                 "self",
                 "switch",
                 "throw",
                 "true",
                 "try",
                 "unsafe",
                 "when",
                 "IDENTIFIER",
                 "LBRACKET",
                 "RPAREN",
                 "SEMICOLON",
                 "this",
                 "super",
                 "IntegerLiteral",
                 "LongLiteral",
                 "FloatingPointLiteral",
                 "DoubleLiteral",
                 "CharacterLiteral",
                 "StringLiteral",
                 "COMMA",
                 "MINUS_MINUS",
                 "PLUS_PLUS",
                 "ARROW",
                 "ArrayInitailizer",
                 "DOT",
                 "LBRACE",
                 "MINUS",
                 "PLUS",
                 "NOT",
                 "COLON",
                 "TWIDDLE",
                 "EQUAL",
                 "PathType",
                 "RBRACE",
                 "future",
                 "EQUAL_EQUAL",
                 "ErrorId",
                 "SynchronizedStatement",
                 "RBRACKET",
                 "SUBTYPE",
                 "SUPERTYPE",
                 "in",
                 "LESS",
                 "LESS_EQUAL",
                 "GREATER",
                 "GREATER_EQUAL",
                 "OR",
                 "NOT_EQUAL",
                 "AND",
                 "MULTIPLY",
                 "XOR",
                 "REMAINDER",
                 "AND_AND",
                 "DIVIDE",
                 "LEFT_SHIFT",
                 "RIGHT_SHIFT",
                 "UNSIGNED_RIGHT_SHIFT",
                 "DARROW",
                 "MINUS_EQUAL",
                 "REMAINDER_EQUAL",
                 "AND_EQUAL",
                 "MULTIPLY_EQUAL",
                 "DIVIDE_EQUAL",
                 "QUESTION",
                 "XOR_EQUAL",
                 "OR_OR",
                 "OR_EQUAL",
                 "PLUS_EQUAL",
                 "LEFT_SHIFT_EQUAL",
                 "RIGHT_SHIFT_EQUAL",
                 "UNSIGNED_RIGHT_SHIFT_EQUAL",
                 "EOF_TOKEN",
                 "RANGE",
                 "any",
                 "current",
                 "ELLIPSIS",
                 "synchronized",
                 "SlComment",
                 "MlComment",
                 "DocComment",
                 "ObjectKind",
                 "ERROR_TOKEN"
             };

    public final static int numTokenKinds = orderedTerminalSymbols.length;
    public final static boolean isValidForParser = true;
}
