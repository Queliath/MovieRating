/**
 * Created by Владислав on 04.07.2016.
 */

var starRating = {
    wrapper: document.getElementById("star-rating"),
    ratingValue: document.getElementById("star-rating").getAttribute("data-value"),
    currentUrl: document.getElementById("star-rating").getAttribute("data-url"),

    stars: [],

    init: function(){
        for(var i = 0; i < 10; i++){
            var star = document.createElement("span");
            star.setAttribute("data-value", Number(i + 1));
            $(star).addClass("glyphicon");
            this.stars[i] = star;
        }
        if(this.ratingValue != null){
            for(var i = 0; i < this.ratingValue; i++){
                $(this.stars[i]).addClass("glyphicon-star");
            }
            for(var i = this.ratingValue; i < 10; i++){
                $(this.stars[i]).addClass("glyphicon-star-empty");
            }
        }
        else{
            for(var i = 0; i < 10; i++){
                $(this.stars[i]).addClass("glyphicon-star-empty");
                this.stars[i].onmouseover = function () {
                    $(this).removeClass("glyphicon-star-empty");
                    $(this).addClass("glyphicon-star");
                }
                this.stars[i].onmouseout = function () {
                    $(this).removeClass("glyphicon-star");
                    $(this).addClass("glyphicon-star-empty");
                }
                var self = this;
                this.stars[i].onclick = function () {
                    var value = this.getAttribute("data-value");
                    var request = new XMLHttpRequest();
                    request.open("GET", self.currentUrl + "&ratingValue=" + value);

                    request.onreadystatechange = function () {
                        if(request.readyState == 4){
                            if(request.status == 200){
                                for(var i = 0; i < value; i++){
                                    $(self.stars[i]).removeClass("glyphicon-star-empty");
                                    $(self.stars[i]).addClass("glyphicon-star");
                                }
                                for(var i = 0; i < 10; i++){
                                    self.stars[i].onmouseover = function () {}
                                    self.stars[i].onmouseout = function () {}
                                    self.stars[i].onclick = function () {}
                                }
                            }
                        }
                    }

                    request.send();
                }
            }
        }
        for(var i = 0; i < 10; i++){
            this.wrapper.appendChild(this.stars[i]);
        }
    }
};
starRating.init();