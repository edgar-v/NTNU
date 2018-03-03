var tasks = [];

var finishedTasks = 0;

function updateText(e) {
    console.log("update");
    console.log(e);
    if (e.target.checked) {
        e.target.parentNode.style.textDecoration = 'line-through';
        finishedTasks += 1;
    } else {
        e.target.parentNode.style.textDecoration = 'none';
        finishedTasks -= 1;
    }
    var outputText = finishedTasks + "/" + tasks.length + " completed";
    document.getElementById("output").innerText = outputText;
}

function addTask() {
    var ul = document.getElementById("todos");
    var li = document.createElement("li");
    var checkbox = document.createElement("input");
    checkbox.type = "checkbox";
    checkbox.addEventListener('click', updateText);
    var text = document.getElementById("todo-text").value;

    task = {};
    task.text = text;
    tasks.push(task);
    tasks.date = new Date();

    li.appendChild(checkbox);
    li.appendChild(document.createTextNode(text));
    ul.insertBefore(li, ul.childNodes[0]);

    var outputText = finishedTasks + "/" + tasks.length + " completed";
    document.getElementById("output").innerText = outputText;
}


document.getElementById("add-task").addEventListener('click', addTask);
