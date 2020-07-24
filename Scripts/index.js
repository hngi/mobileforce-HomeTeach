$('document').ready(function() {
    var typed = new Typed('#typed', {
        strings: ['Anytime... ^1000', 'Anywhere...'],
        smartBackspace: true,
        backSpeed: 100,
        typeSpeed: 200,
        // fadeOut: true,

        loop: true,
    });
})

var $carousel = $('.carousel').flickity({
    imagesLoaded: true,
    percentPosition: false,
    freeScroll: true,
    contain: true,
    autoPlay: true,
    pauseAutoPlayOnHover: true,

});

var $carousels = $('.carousels').flickity({
    imagesLoaded: true,
    percentPosition: false,
    freeScroll: true,
    contain: true,
    autoPlay: true,
    pauseAutoPlayOnHover: true,

});