/**
 * Created by SonamPC on 21-Mar-18.
 */

$(document).ready(function () {

    //region === Menu Heighlighting ===
    let url = window.location.pathname + window.location.search;
    let menuLink = $('.sidebar-section').children('.nav-sidebar')
        .children('.nav-item-submenu').children('ul')
        .children('li').children('a');

    $.each(menuLink, function () {
        renderLink($(this));
    });

    let firstLevelLink = $('.nav-item-submenu')
        .children('.nav-group-sub').children('.nav-item-submenu')
        .children('.nav-group-sub').children('.nav-item').children('a');

    $.each(firstLevelLink, function () {
        renderLink($(this));
    });
    $.each(firstLevelLink, function () {
        renderLink($(this));
    });

    function renderLink($this) {

        if ($this.attr('href') === url) {
            $this.closest('.nav-item').children('.nav-link').addClass("active");
            $this.closest('.nav-item-submenu').addClass("nav-item-expanded ");
            $this.closest('.nav-item-submenu').addClass("nav-item-open");

            $this.closest('.nav-item-submenu').parent('.nav-group-sub').parent(".nav-item-submenu").addClass("nav-item-expanded");

            $this.css({
                "color": "#D18303",
                "font-weight": "bold"
            });
        }
    }
});

