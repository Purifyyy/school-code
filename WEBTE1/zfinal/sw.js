const cache_container = "static_v2  ";

const files = [
    "./",
    "./index.html",
    "./manifest.json",
    "./styles.css",
    "./script.js",
    "./logo.png",
    "./back.png",
    "./help/",
    "./help/helpA.png", "./help/helpB.png", "./help/helpC.png", "./help/helpD.png", "./help/helpE.png", "./help/helpF.png", "./help/helpG.png", "./help/helpH.png", "./help/helpI.png", "./help/helpJ.png",
    "./images/a/", 
    "./images/a/a0.png", "./images/a/a1.png", "./images/a/a2.png", "./images/a/a6.png", "./images/a/a7.png", "./images/a/a9.png", "./images/a/a10.png", "./images/a/a12.png",
    "./images/b/", 
    "./images/b/b1.png", "./images/b/b4.png", "./images/b/b9.png", "./images/b/b11.png", "./images/b/b12.png", "./images/b/b16.png", "./images/b/b40.png", "./images/b/b52.png",
    "./images/c/", 
    "./images/c/c0.png", "./images/c/c1.png", "./images/c/c4.png", "./images/c/c24.png", "./images/c/c30.png", "./images/c/c55.png", "./images/c/c80.png", "./images/c/c81.png",
    "./images/d/", 
    "./images/d/d0.png", "./images/d/d4.png", "./images/d/d8.png", "./images/d/d20.png", "./images/d/d21.png", "./images/d/d22.png", "./images/d/d25.png", "./images/d/d30.png",
    "./images/e/", 
    "./images/e/e0.png", "./images/e/e4.png", "./images/e/e5.png", "./images/e/e8.png", "./images/e/e11.png", "./images/e/e16.png", "./images/e/e17.png", "./images/e/e33.png",
    "./images/f/", 
    "./images/f/f0.png", "./images/f/f4.png", "./images/f/f6.png", "./images/f/f10.png", "./images/f/f17.png", "./images/f/f18.png", "./images/f/f28.png", "./images/f/f30.png",
    "./images/g/", 
    "./images/g/g0.png", "./images/g/g1.png", "./images/g/g2.png", "./images/g/g3.png", "./images/g/g5.png", "./images/g/g23.png", "./images/g/g51.png", "./images/g/g54.png",
    "./images/h/", 
    "./images/h/h0.png", "./images/h/h1.png", "./images/h/h2.png", "./images/h/h5.png", "./images/h/h8.png", "./images/h/h12.png", "./images/h/h40.png", "./images/h/h65.png",
    "./images/i/", 
    "./images/i/i2.png", "./images/i/i3.png", "./images/i/i9.png", "./images/i/i15.png", "./images/i/i16.png", "./images/i/i21.png", "./images/i/i39.png", "./images/i/i51.png",
    "./images/j/", 
    "./images/j/j0.png", "./images/j/j2.png", "./images/j/j5.png", "./images/j/j7.png", "./images/j/j14.png", "./images/j/j18.png", "./images/j/j39.png", "./images/j/j40.png",
    "./images.json",
    "./apple-touch-icon.png", "./favicon-16x16.png", "./favicon-32x32.png", "./safari-pinned-tab.svg",
    "https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css",
    "https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js",
    "https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js",
    "https://cdn.jsdelivr.net/npm/sortablejs@latest/Sortable.min.js"
]

self.addEventListener('install', function(event){
    event.waitUntil(
        caches.open(cache_container)
        .then(cache => {
            return cache.addAll(files)
        })
    )
})

self.addEventListener('fetch', (event) => {
    event.respondWith(
      caches.match(event.request).then((response) => {
        return response || fetch(event.request);
      })
    );
});