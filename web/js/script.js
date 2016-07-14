/**
 * Created by Владислав on 04.07.2016.
 */

var starRating = {
    wrapper: document.getElementById("star-rating"),
    ratingValue: document.getElementById("star-rating").getAttribute("data-value"),

    init: function(){
        var stars = [];
        for(var i = 0; i < 10; i++){
            var star = document.createElement("span");
            $(star).addClass("glyphicon");
            stars[i] = star;
        }
        if(this.ratingValue != null){
            for(var i = 0; i < this.ratingValue; i++){
                $(stars[i]).addClass("glyphicon-star");
            }
            for(var i = this.ratingValue; i < 10; i++){
                $(stars[i]).addClass("glyphicon-star-empty");
            }
        }
        else{
            for(var i = 0; i < 10; i++){
                $(stars[i]).addClass("glyphicon-star-empty");
                stars[i].onmouseover = function () {
                    $(this).removeClass("glyphicon-star-empty");
                    $(this).addClass("glyphicon-star");
                }
                stars[i].onmouseout = function () {
                    $(this).removeClass("glyphicon-star");
                    $(this).addClass("glyphicon-star-empty");
                }
            }
        }
        for(var i = 0; i < 10; i++){
            this.wrapper.appendChild(stars[i]);
        }
    }
};
starRating.init();