var imgObjects = []
const equations = document.getElementById("equations")
var modal = new bootstrap.Modal(document.getElementById("staticBackdrop"), {
    keyboard: false,
    backdrop: 'static',
    focus: true
})
const nextTask = document.getElementById("nextTask")
const hintImage = document.getElementById("hintImg")
const hintButton = document.getElementById("help")

var tasks = localStorage.getItem("tasks") == null ? [] : JSON.parse(localStorage.getItem("tasks"))
var task
var currTask, results = []

new Sortable(equations , {
    animation: 150,
    ghostClass: 'blue-background-class'
});

// Tuto funkciu som prevzal z https://stackoverflow.com/a/40093024
function arrayShuffle(o) {
    for(var j, x, i = o.length; i; j = parseInt(Math.random() * i), x = o[--i], o[i] = o[j], o[j] = x);
    return o;
}

const loadImages = () => {
    currTask = tasks.shift()
    if(currTask === undefined){
        tasks = ['A','B','C','D','E','F','G','H','I','J']
        arrayShuffle(tasks)
        localStorage.setItem("tasks", JSON.stringify(tasks));
        currTask = tasks.shift()
    }
    fetch("./images.json")
    .then(res => res.json())
    .then(data => {
        results = []
        task = data[currTask]
        task.forEach(image => {
            results.push(image.result)
            appendImage(image);
        })
        arrayShuffle(imgObjects)
        equations.innerHTML = ''
        for(i = 0; i < imgObjects.length ; i++) {
            equations.appendChild(imgObjects[i])
        }
        imgObjects = []
    })
}

function appendImage(jsonImg){
    var equation = document.createElement("img");
    equation.src = `./images/${jsonImg.filename}`;
    equation.style = "object-fit: none"
    equation.style.cursor = "pointer";
    equation.draggable = true
    equation.setAttribute("class", "row");
    equation.setAttribute("result", jsonImg.result)
    //equation.setAttribute("data-description", jsonImg.description)

    imgObjects.push(equation)
}

equations.addEventListener("change", () => {
    var list = equations.querySelectorAll("img")
    for(var i = 0; i < results.length; i++){
        if(list[i].getAttribute("result") != results[i]){
            return;
        }
    }
    localStorage.setItem("tasks", JSON.stringify(tasks));
    setTimeout(function() {
        modal.show();
    }, 500);  
})

nextTask.addEventListener("click", () => {
    modal.hide()
    loadImages()
})

hintButton.addEventListener("click", () => {
    hintImage.innerHTML = ''
    var newImg = document.createElement("img")
    newImg.src = "./help/help"+currTask+".png"
    hintImage.appendChild(newImg)
})

loadImages()