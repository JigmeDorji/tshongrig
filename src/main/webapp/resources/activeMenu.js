/**
 * Created by SonamPC on 21-Mar-18.
 */

$(document).ready(function () {

    //region === Menu Heighlighting ===
    let url = window.location.pathname + window.location.search;
    let menuLink = $('.panel-menu-level-3,.panel-menu-level-2')
        .children('.panel-heading').next().children(
            '.panel-body').children('ul')
        .children('li').children('a');

    $.each(menuLink, function () {
        renderLink($(this));
    });

    let firstLevelLink = $('.panel-menu-level-2')
        .children('.panel-heading').children('.panel-title').children('a');

    $.each(firstLevelLink, function () {
        renderLink($(this));
    });

    function renderLink($this) {
        if ($this.attr('href') === url) {
            $this.closest('.panel-menu').children('.panel-heading').addClass("panel-heading-active");
            $this.closest('.panel-menu').children('.panel-collapse').addClass("in");
            $this.closest('.panel-collapse').addClass("in");

            $this.css({
                "color": "#D18303",
                "font-weight": "bold"
            });
            $this.closest('.panel-collapse').parent(
                '.panel-menu-level-3').closest(
                '.panel-collapse').addClass("in");
        }
    }

    //endregion === Menu Heighlighting ===
});

