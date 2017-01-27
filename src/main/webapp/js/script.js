/**
 * Created by Владислав on 04.07.2016.
 */

var starRating = {
    wrapper: document.getElementById("star-rating"),
    ratingValue: document.getElementById("star-rating").getAttribute("data-value"),
    currentUrl: document.getElementById("star-rating").getAttribute("data-url"),
    movieId: document.getElementById("star-rating").getAttribute("data-movie-id"),

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
                    request.open("GET", self.currentUrl + "&ratingValue=" + value + "&movieId=" + self.movieId);

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

        this.wrapper.innerHTML = "";
        for(var i = 0; i < 10; i++){
            this.wrapper.appendChild(this.stars[i]);
        }

        if(this.ratingValue != null){
            var deleteRatingLinkWrapper = document.createElement("p");
            var deleteRatingLink = document.createElement("a");
            deleteRatingLink.setAttribute("href", "#");
            deleteRatingLink.innerHTML = "Удалить рейтинг";

            var self = this;
            deleteRatingLink.onclick = function () {
                var request = new XMLHttpRequest();
                request.open("GET", "Controller?command=delete-rating&movieId=" + self.movieId);
                request.onreadystatechange = function () {
                    if(request.readyState == 4){
                        if(request.status == 200){
                            self.ratingValue = null;
                            self.init();
                        }
                    }
                }
                request.send();
            }
            
            deleteRatingLinkWrapper.appendChild(deleteRatingLink);
            this.wrapper.appendChild(deleteRatingLinkWrapper);
        }
    }
};
starRating.init();