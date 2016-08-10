/**
 * Created by Владислав on 06.08.2016.
 */
var remove = {
    removeButtons: document.querySelectorAll(".btn[data-toggle='modal'], a[data-toggle='modal'"),
    
    init: function () {
        for(var i = 0; i < this.removeButtons.length; i++){
            this.removeButtons[i].onclick = function () {
                var id = this.getAttribute("data-id");
                var modalSelector = this.getAttribute("data-target");

                var idHiddenInput = document.querySelector(modalSelector + " form input[name='id']");
                idHiddenInput.setAttribute("value", id);
            }
        }
    }
};
remove.init();