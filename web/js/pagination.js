/**
 * Created by Владислав on 22.07.2016.
 */
var pagination = {
    searchForm: document.getElementById("search-form"),
    pageInput: document.querySelector("#search-form input[name='page']"),
    elements: document.querySelectorAll(".pagination li a"),

    init: function () {
        var self = this;
        for(var i = 0; i < this.elements.length; i++){
            this.elements[i].onclick = function () {
                self.pageInput.setAttribute("value", this.innerHTML);
                self.searchForm.submit();
                return false;
            }
        }
    }
};
pagination.init();