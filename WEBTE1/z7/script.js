const photos = document.getElementById("photos")
const filter = document.getElementById("filter")
const filterValue = localStorage.getItem("filter") == null ? "" : localStorage.getItem("filter")
filter.value = filterValue
var order = []
var xPhoto = []
var xPhotoFull = []
const nextPhoto = document.getElementById("next")
const previousPhoto = document.getElementById("previous")
const slideShowButton = document.getElementById("slideshow-start")
const closeModal = document.getElementById("modalButtonClose")
var interval
var modalTitle = document.getElementById("staticBackdropLabel")
var modalBody = document.getElementById("modalImg")
var lastClicked;
var slideShowON = 0

new Sortable(photos , {
    animation: 150,
    ghostClass: 'blue-background-class'
});

const loadImages = (filterString) => {
    localStorage.setItem("filter", filterString)
    photos.innerHTML = ''
    fetch("./images.json")
    .then(res => res.json())
    .then(data => {
        data.forEach(image => {
            if(image.title.indexOf(filterString) != -1){ 
                appendImage(image);
            }
        })
        var itemsArray = sortPhotos(xPhoto)
        xPhotoFull = itemsArray
        for(i = 0;i<itemsArray.length;i++) {
            photos.appendChild(itemsArray[i])
        }
        xPhoto = []
    })
}

function sortPhotos(photoArr){
    var sortArr = localStorage.getItem("order") == undefined ? [] : localStorage.getItem("order")
    photoArr.sort(function(a, b){  
        return sortArr.indexOf(a.id) - sortArr.indexOf(b.id);
    });
    return photoArr
}

filter.addEventListener("input", event => {
    loadImages(event.target.value)
})

function appendImage(jsonImg){
    var thumbnail = document.createElement("img");
    thumbnail.id = jsonImg.id
    thumbnail.src = `./images/${jsonImg.filename}`;
    thumbnail.alt = jsonImg.description
    thumbnail.style = "object-fit: cover"
    thumbnail.style.cursor = "pointer";
    thumbnail.draggable = true
    thumbnail.setAttribute("class", "col");
    thumbnail.setAttribute("data-bs-toggle", "modal")
    thumbnail.setAttribute("data-bs-target", "#staticBackdrop")
    thumbnail.setAttribute("title", jsonImg.title)
    thumbnail.setAttribute("data-description", jsonImg.description)

    thumbnail.addEventListener("click", () => {
        modalTitle.innerHTML = jsonImg.title+" - "+jsonImg.description
        var fullImage = document.createElement("img")
        fullImage.src = `./images/${jsonImg.filename}`;
        modalBody.innerHTML = ""
        modalBody.appendChild(fullImage)
        lastClicked = xPhotoFull.indexOf(thumbnail)
    })

    xPhoto.push(thumbnail)
}

function ordr(){
    var list = document.getElementById("photos").querySelectorAll("img")
    order = []
    for(i = 0; i<list.length;i++){
        if(!order.includes(list[i].id)){
            order.push(list[i].id)
        }
    }
    localStorage.setItem("order",order)
    sortPhotos(xPhotoFull)
}

function goToNext(){
    var howMany = xPhotoFull.length
    var nextImageIndex = lastClicked+1
    if(nextImageIndex >= howMany){
        lastClicked = 0
        var nextImg = xPhotoFull[lastClicked]
        changeModalContent(nextImg)
    } else {
        var nextImg = xPhotoFull[nextImageIndex]
        changeModalContent(nextImg)
        lastClicked++
    }
    if(lastClicked == howMany-1){
        lastClicked = -1
    }
}

nextPhoto.addEventListener("click", () => {
    goToNext()
})

previousPhoto.addEventListener("click", () => {
    var howMany = xPhotoFull.length
    if(howMany > 1) {
        if(lastClicked < 1){
            if(lastClicked == -1){
                lastClicked = howMany-1
            } else {
                lastClicked = howMany
            }
        }
        var nextImageIndex = lastClicked-1
        var nextImg = xPhotoFull[nextImageIndex]
        changeModalContent(nextImg)
        lastClicked--
    }
})

function changeModalContent(nextImg){
    modalTitle.innerHTML = nextImg.title+ " - " + nextImg.getAttribute("data-description")
    modalBody.innerHTML = ""
    var newImg = document.createElement("img")
    newImg.src = nextImg.src;
    modalBody.appendChild(newImg)
}

const counterFunction = () => {
    goToNext()
}

slideShowButton.addEventListener("click", () => {
    if(slideShowON){
        clearInterval(interval);
        slideShowON = 0
        slideShowButton.innerHTML = "►"
    } else {
        interval = setInterval(counterFunction,2000)
        slideShowON = 1
        slideShowButton.innerHTML = "||"
    }
})

closeModal.addEventListener("click", () => {
    clearInterval(interval);
    slideShowON = 0
    slideShowButton.innerHTML = "►"
})

loadImages(filterValue)