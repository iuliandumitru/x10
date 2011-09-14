/*
 *  This file is part of the X10 project (http://x10-lang.org).
 *
 *  This file is licensed to You under the Eclipse Public License (EPL);
 *  You may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *      http://www.opensource.org/licenses/eclipse-1.0.php
 *
 *  (C) Copyright IBM Corporation 2006-2010.
 */
/********************************************************************
 * WARNING!  THIS JAVA FILE IS AUTO-GENERATED FROM x10/parser/x10.g *
 ********************************************************************/

package x10.parser;

public interface X10Parsersym {
    public final static int
      TK_IntegerLiteral = 36,
      TK_LongLiteral = 37,
      TK_ByteLiteral = 38,
      TK_ShortLiteral = 39,
      TK_UnsignedIntegerLiteral = 40,
      TK_UnsignedLongLiteral = 41,
      TK_UnsignedByteLiteral = 42,
      TK_UnsignedShortLiteral = 43,
      TK_FloatingPointLiteral = 44,
      TK_DoubleLiteral = 45,
      TK_CharacterLiteral = 46,
      TK_StringLiteral = 47,
      TK_MINUS_MINUS = 55,
      TK_OR = 27,
      TK_MINUS = 8,
      TK_MINUS_EQUAL = 96,
      TK_NOT = 23,
      TK_NOT_EQUAL = 79,
      TK_REMAINDER = 25,
      TK_REMAINDER_EQUAL = 97,
      TK_AND = 28,
      TK_AND_AND = 80,
      TK_AND_EQUAL = 98,
      TK_LPAREN = 1,
      TK_RPAREN = 3,
      TK_MULTIPLY = 24,
      TK_MULTIPLY_EQUAL = 99,
      TK_COMMA = 6,
      TK_DOT = 34,
      TK_DIVIDE = 26,
      TK_DIVIDE_EQUAL = 100,
      TK_COLON = 75,
      TK_SEMICOLON = 7,
      TK_QUESTION = 101,
      TK_AT = 4,
      TK_LBRACKET = 2,
      TK_RBRACKET = 57,
      TK_XOR = 29,
      TK_XOR_EQUAL = 102,
      TK_LBRACE = 58,
      TK_OR_OR = 76,
      TK_OR_EQUAL = 103,
      TK_RBRACE = 60,
      TK_TWIDDLE = 30,
      TK_PLUS = 9,
      TK_PLUS_PLUS = 56,
      TK_PLUS_EQUAL = 104,
      TK_LESS = 61,
      TK_LEFT_SHIFT = 62,
      TK_LEFT_SHIFT_EQUAL = 105,
      TK_RIGHT_SHIFT = 63,
      TK_RIGHT_SHIFT_EQUAL = 106,
      TK_UNSIGNED_RIGHT_SHIFT = 64,
      TK_UNSIGNED_RIGHT_SHIFT_EQUAL = 107,
      TK_LESS_EQUAL = 65,
      TK_EQUAL = 53,
      TK_EQUAL_EQUAL = 59,
      TK_GREATER = 66,
      TK_GREATER_EQUAL = 67,
      TK_ELLIPSIS = 146,
      TK_RANGE = 68,
      TK_ARROW = 69,
      TK_DARROW = 122,
      TK_SUBTYPE = 83,
      TK_SUPERTYPE = 90,
      TK_STARSTAR = 77,
      TK_NTWIDDLE = 81,
      TK_LARROW = 70,
      TK_FUNNEL = 71,
      TK_LFUNNEL = 72,
      TK_DIAMOND = 73,
      TK_BOWTIE = 74,
      TK_RANGE_EQUAL = 108,
      TK_ARROW_EQUAL = 109,
      TK_STARSTAR_EQUAL = 110,
      TK_TWIDDLE_EQUAL = 111,
      TK_LARROW_EQUAL = 112,
      TK_FUNNEL_EQUAL = 113,
      TK_LFUNNEL_EQUAL = 114,
      TK_DIAMOND_EQUAL = 115,
      TK_BOWTIE_EQUAL = 116,
      TK_abstract = 12,
      TK_as = 117,
      TK_assert = 133,
      TK_async = 123,
      TK_at = 82,
      TK_athome = 147,
      TK_ateach = 124,
      TK_atomic = 11,
      TK_break = 134,
      TK_case = 91,
      TK_catch = 125,
      TK_class = 86,
      TK_clocked = 10,
      TK_continue = 135,
      TK_def = 126,
      TK_default = 92,
      TK_do = 127,
      TK_else = 136,
      TK_extends = 128,
      TK_false = 48,
      TK_final = 13,
      TK_finally = 129,
      TK_finish = 78,
      TK_for = 130,
      TK_goto = 148,
      TK_haszero = 93,
      TK_here = 49,
      TK_if = 137,
      TK_implements = 138,
      TK_import = 94,
      TK_in = 131,
      TK_instanceof = 95,
      TK_interface = 119,
      TK_native = 14,
      TK_new = 33,
      TK_null = 50,
      TK_offer = 139,
      TK_offers = 140,
      TK_operator = 31,
      TK_package = 132,
      TK_private = 15,
      TK_property = 120,
      TK_protected = 16,
      TK_public = 17,
      TK_return = 141,
      TK_self = 51,
      TK_static = 18,
      TK_struct = 87,
      TK_super = 35,
      TK_switch = 142,
      TK_this = 32,
      TK_throw = 143,
      TK_transient = 19,
      TK_true = 52,
      TK_try = 144,
      TK_type = 88,
      TK_val = 84,
      TK_var = 85,
      TK_void = 54,
      TK_when = 145,
      TK_while = 121,
      TK_unitfor = 20,
      TK_atomicplus = 21,
      TK_linked = 22,
      TK_EOF_TOKEN = 118,
      TK_IDENTIFIER = 5,
      TK_SlComment = 149,
      TK_MlComment = 150,
      TK_DocComment = 151,
      TK_PseudoDoubleLiteral = 152,
      TK_ErrorId = 89,
      TK_ERROR_TOKEN = 153;

    public final static String orderedTerminalSymbols[] = {
                 "",
                 "LPAREN",
                 "LBRACKET",
                 "RPAREN",
                 "AT",
                 "IDENTIFIER",
                 "COMMA",
                 "SEMICOLON",
                 "MINUS",
                 "PLUS",
                 "clocked",
                 "atomic",
                 "abstract",
                 "final",
                 "native",
                 "private",
                 "protected",
                 "public",
                 "static",
                 "transient",
                 "unitfor",
                 "atomicplus",
                 "linked",
                 "NOT",
                 "MULTIPLY",
                 "REMAINDER",
                 "DIVIDE",
                 "OR",
                 "AND",
                 "XOR",
                 "TWIDDLE",
                 "operator",
                 "this",
                 "new",
                 "DOT",
                 "super",
                 "IntegerLiteral",
                 "LongLiteral",
                 "ByteLiteral",
                 "ShortLiteral",
                 "UnsignedIntegerLiteral",
                 "UnsignedLongLiteral",
                 "UnsignedByteLiteral",
                 "UnsignedShortLiteral",
                 "FloatingPointLiteral",
                 "DoubleLiteral",
                 "CharacterLiteral",
                 "StringLiteral",
                 "false",
                 "here",
                 "null",
                 "self",
                 "true",
                 "EQUAL",
                 "void",
                 "MINUS_MINUS",
                 "PLUS_PLUS",
                 "RBRACKET",
                 "LBRACE",
                 "EQUAL_EQUAL",
                 "RBRACE",
                 "LESS",
                 "LEFT_SHIFT",
                 "RIGHT_SHIFT",
                 "UNSIGNED_RIGHT_SHIFT",
                 "LESS_EQUAL",
                 "GREATER",
                 "GREATER_EQUAL",
                 "RANGE",
                 "ARROW",
                 "LARROW",
                 "FUNNEL",
                 "LFUNNEL",
                 "DIAMOND",
                 "BOWTIE",
                 "COLON",
                 "OR_OR",
                 "STARSTAR",
                 "finish",
                 "NOT_EQUAL",
                 "AND_AND",
                 "NTWIDDLE",
                 "at",
                 "SUBTYPE",
                 "val",
                 "var",
                 "class",
                 "struct",
                 "type",
                 "ErrorId",
                 "SUPERTYPE",
                 "case",
                 "default",
                 "haszero",
                 "import",
                 "instanceof",
                 "MINUS_EQUAL",
                 "REMAINDER_EQUAL",
                 "AND_EQUAL",
                 "MULTIPLY_EQUAL",
                 "DIVIDE_EQUAL",
                 "QUESTION",
                 "XOR_EQUAL",
                 "OR_EQUAL",
                 "PLUS_EQUAL",
                 "LEFT_SHIFT_EQUAL",
                 "RIGHT_SHIFT_EQUAL",
                 "UNSIGNED_RIGHT_SHIFT_EQUAL",
                 "RANGE_EQUAL",
                 "ARROW_EQUAL",
                 "STARSTAR_EQUAL",
                 "TWIDDLE_EQUAL",
                 "LARROW_EQUAL",
                 "FUNNEL_EQUAL",
                 "LFUNNEL_EQUAL",
                 "DIAMOND_EQUAL",
                 "BOWTIE_EQUAL",
                 "as",
                 "EOF_TOKEN",
                 "interface",
                 "property",
                 "while",
                 "DARROW",
                 "async",
                 "ateach",
                 "catch",
                 "def",
                 "do",
                 "extends",
                 "finally",
                 "for",
                 "in",
                 "package",
                 "assert",
                 "break",
                 "continue",
                 "else",
                 "if",
                 "implements",
                 "offer",
                 "offers",
                 "return",
                 "switch",
                 "throw",
                 "try",
                 "when",
                 "ELLIPSIS",
                 "athome",
                 "goto",
                 "SlComment",
                 "MlComment",
                 "DocComment",
                 "PseudoDoubleLiteral",
                 "ERROR_TOKEN"
             };

    public final static int numTokenKinds = orderedTerminalSymbols.length;
    public final static boolean isValidForParser = true;
}
