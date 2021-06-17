// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Initial
    @8191               // (256 row * 32 col) - 1 
    D=A                 // each col have 16 bits
    @total
    M=D                 // Total screen pixel size

(EVENT)
    @SCREEN
    D=A
    @addr
    M=D                 // Screen base

    @R0
    D=A
    @index
    M=D                 // index = 0

    @KBD
    D=M

    @FILL_WHITE
    D;JEQ

    @FILL_BLACK
    0;JMP

(FILL_WHITE)
    @color
    M=0
    @FILL_SCREEN
    0;JMP

(FILL_BLACK)
    @color
    M=-1

(FILL_SCREEN)
    @index
    D=M
    @total
    D=D-M
    @EVENT
    D;JGT

    @color
    D=M

    @addr
    A=M
    M=D

    @index
    M=M+1

    @addr
    M=M+1
    
    @FILL_SCREEN
    0;JMP