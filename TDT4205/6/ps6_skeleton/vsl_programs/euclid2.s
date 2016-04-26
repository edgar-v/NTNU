.section .rodata
intout: .string "%ld "
strout: .string "%s "
errout: .string "Wrong number of arguments"
.section .data
.globl main
.section .text
main:
	pushq %rbp
	movq %rsp, %rbp
	subq $1, %rdi
	cmpq	$2,%rdi
	jne ABORT
	cmpq $0, %rdi
	jz SKIP_ARGS
	movq %rdi, %rcx
	addq $16, %rsi
PARSE_ARGV:
	pushq %rcx
	pushq %rsi
	movq (%rsi), %rdi
	movq $0, %rsi
	movq $10, %rdx
	call strtol
	popq %rsi
	popq %rcx
	pushq %rax
	subq $8, %rsi
	loop PARSE_ARGV
	popq	%rdi
	popq	%rsi
SKIP_ARGS:
	call	_gcd
	jmp END
ABORT:
	movq $errout, %rdi
	call puts
END:
	movq %rax, %rdi
	call exit
_lol:
	pushq	%rbp
	movq	%rsp, %rbp
	pushq	%rdi
	pushq	%rsi
	movq	-8(%rbp), %rax
	pushq	%rax
	movq	-16(%rbp), %rax
	addq	%rax, (%rsp)
	popq	%rax
	leave
	ret
_gcd:
	pushq	%rbp
	movq	%rsp, %rbp
	pushq	%rdi
	pushq	%rsi
	pushq	$0
	pushq	$0
	pushq	$0
	pushq	$0
	movq	-16(%rbp), %rax
	pushq	%rax
	movq	-8(%rbp), %rax
	subq	%rax, (%rsp)
	popq	%rax
	movq	%rax, -24(%rbp)
	movq	-8(%rbp), %rdi
	movq	-16(%rbp), %rsi
	call	_lol
	movq	%rax, -32(%rbp)
	movq	-32(%rbp), %rsi
	movq	$intout, %rdi
	call	printf
	movq	$'\n', %rdi
	call	putchar
	movq	-24(%rbp), %rdi
	movq	-8(%rbp), %rsi
	call	_lol
	pushq	%rax
	movq	-16(%rbp), %rax
	addq	%rax, (%rsp)
	popq	%rax
	movq	%rax, -32(%rbp)
	movq	-32(%rbp), %rsi
	movq	$intout, %rdi
	call	printf
	movq	$'\n', %rdi
	call	putchar
	movq	-8(%rbp), %rsi
	movq	$intout, %rdi
	call	printf
	movq	$'\n', %rdi
	call	putchar
	movq	-16(%rbp), %rsi
	movq	$intout, %rdi
	call	printf
	movq	$'\n', %rdi
	call	putchar
	movq	-24(%rbp), %rsi
	movq	$intout, %rdi
	call	printf
	movq	$'\n', %rdi
	call	putchar
	movq	-40(%rbp), %rsi
	movq	$intout, %rdi
	call	printf
	movq	$'\n', %rdi
	call	putchar
	movq	-24(%rbp), %rax
	leave
	ret
