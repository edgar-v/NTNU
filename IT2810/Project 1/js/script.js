function loadArticle(articleName) {
    document.getElementById("home").style.display = "none"; 
    document.getElementById("reasons").style.display = "none"; 
    document.getElementById("about").style.display = "none"; 

    document.getElementById(articleName).style.display = "block"; 
}

function init() {
    if (window.location.hash) {
        loadArticle(window.location.hash.substring(1));
    }

    document.getElementById("home-button").onclick = function() {
        loadArticle("home");
    };
    document.getElementById("reasons-button").onclick = function() {
        loadArticle("reasons");
    };
    document.getElementById("about-button").onclick = function() {
        loadArticle("about");
    }
}

window.onload = init;
