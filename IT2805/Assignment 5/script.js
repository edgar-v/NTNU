/* Part 2 */
console.log('PART 2')
var i;

for (i = 1; i <= 20; i++) {
    console.log(i);
}

/* Part 3 */
console.log('PART 3')

const numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]

for (i = 0; i < numbers.length; i++) {
    if (numbers[i] % 3 === 0) {
        console.log("eple");
    } else if (numbers[i] % 5 === 0) {
        console.log("kake");
    } else {
        console.log(numbers[i]);
    }
}

/* Part 4 */
document.getElementById("title").innerText += "Hello, Javascript";

/* Part 5 */
function changeDisplay () {
    document.getElementById("magic").style.display = "none";
}

function changeVisibility () {
    document.getElementById("magic").style.visibility = "hidden";
    document.getElementById("magic").style.display = "block";
}

function reset () {
    document.getElementById("magic").style.visibility = "visible";
    document.getElementById("magic").style.display = "block";
}

/* Part 6 */
const technologies = [
    'HTML5',
    'CSS3',
    'JavaScript',
    'Python',
    'Java',
    'AJAX',
    'JSON',
    'React',
    'Angular',
    'Bootstrap',
    'Node.js'
];

for (i = 0; i < technologies.length; i++) {
    /* Create a node and fill it with text */
    node = document.createElement("li");
    node.innerText = technologies[i];

    document.getElementById("tech").appendChild(node);
}
