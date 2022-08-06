/**
 * Created by Bikash Rai on 3/13/2022.
 */

let body = $('body');

let shiftKey = 32;

function baseURL() {
    return spms.getUrl();
}


body.dbKeypress(shiftKey, function (e) {
    spms.ajax(baseURL() + 'getScreenList', 'GET', {}
        , function (res) {

            $('#screenListModal').modal('show');
            $('#autoCompleteScreenList').devbridgeAutocomplete({
                lookup: $.map(res, function (value) {
                    return {data: value.id, value: value.text}
                }), onSelect: function (suggestion) {
                    window.location.href = spms.getUrl() + suggestion.data
                }
            });
            setTimeout(function () {
                $('#autoCompleteScreenList').focus();
            }, 500)
        })
});
