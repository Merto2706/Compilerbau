package de.thm.mni.compilerbau.phases._01_scanner;

import de.thm.mni.compilerbau.absyn.IntLiteral;
import de.thm.mni.compilerbau.utils.SplError;
import de.thm.mni.compilerbau.phases._02_03_parser.Sym;
import de.thm.mni.compilerbau.absyn.Position;
import de.thm.mni.compilerbau.table.Identifier;
import de.thm.mni.compilerbau.CommandLineOptions;
import java_cup.runtime.*;

%%


%class Scanner
%public
%line
%column
%cup
%eofval{
    return new java_cup.runtime.Symbol(Sym.EOF, yyline + 1, yycolumn + 1);   //This needs to be specified when using a custom sym class name
%eofval}

%{
    public CommandLineOptions options = null;

    private Symbol symbol(int type) {
      return new Symbol(type, yyline + 1, yycolumn + 1);
    }

    private Symbol symbol(int type, Object value) {
      return new Symbol(type, yyline + 1, yycolumn + 1, value);
    }
%}

%%

// The regular expressions for all tokens need to be defined here.
array                 {return symbol(Sym.ARRAY);}
else                  {return symbol(Sym.ELSE);}
if                    {return symbol(Sym.IF);}
of                    {return symbol(Sym.OF);}
proc                  {return symbol(Sym.PROC);}
ref                   {return symbol(Sym.REF);}
type                  {return symbol(Sym.TYPE);}
var                   {return symbol(Sym.VAR);}
while                 {return symbol(Sym.WHILE);}

"//".*                {/* Einlinienkommenare werden nicht ausgegeben */}
[ \t\n\r]             { /* SPACE, TAB und NEWLINE */ }
"("                   {return symbol(Sym.LPAREN);}
")"                   {return symbol(Sym.RPAREN);}
"["                   {return symbol(Sym.LBRACK);}
"]"                   {return symbol(Sym.RBRACK);}
"{"                   {return symbol(Sym.LCURL);}
"}"                   {return symbol(Sym.RCURL);}
"="                   {return symbol(Sym.EQ);}
"#"                   {return symbol(Sym.NE);}
"<"                   {return symbol(Sym.LT);}
"<="                  {return symbol(Sym.LE);}
">"                   {return symbol(Sym.GT);}
">="                  {return symbol(Sym.GE);}
":="                  {return symbol(Sym.ASGN);}
":"                   {return symbol(Sym.COLON);}
","                   {return symbol(Sym.COMMA);}
";"                   {return symbol(Sym.SEMIC);}
"+"                   {return symbol(Sym.PLUS);}
"-"                   {return symbol(Sym.MINUS);}
"*"                   {return symbol(Sym.STAR);}
"/"                   {return symbol(Sym.SLASH);}

'\\n'                 {return symbol(Sym.INTLIT, 10);} // NEWLINE als Char
'.'                   {return symbol(Sym.INTLIT, (int) yycharat(1));}

[0-9]+                {return symbol(Sym.INTLIT, Integer.parseInt(yytext()));}
0x[0-9A-Fa-f]+        {return symbol(Sym.INTLIT,  Integer.parseInt(yytext().substring(2, yylength()), 16));} //Zahlen von 0-15 hex format nicht leer!
[A-Za-z_0-9]*         {return symbol(Sym.IDENT, new Identifier(yytext()));}

[^]	               	  {throw SplError.IllegalCharacter(new Position(yyline + 1, yycolumn + 1), yytext().charAt(0));}