.section .rodata
strout: .string "%li"
.section .text
.globl fibonacci

fibonacci:
	pushq %rbp
    movq %rsp, %rbp
	movq $0, %rax	/* f(0) */
	movq $1, %rbx	/* f(1) */
	movq $2, %rcx	/* Counter */
LOOP:
	cmp $50, %rcx
	jz END
	movq %rbx, %rdx /* rdx = rbx */
	addq %rax, %rbx /* rbx += rax */
	movq %rdx, %rax /* rax = rdx */
	addq $1, %rcx
	jmp LOOP

END:
	movq %rbx, %rsi
	movq $strout, %rdi
	call printf
	movq $'\n', %rdi
	call putchar
	movq $0, %rax
	leave
	ret
