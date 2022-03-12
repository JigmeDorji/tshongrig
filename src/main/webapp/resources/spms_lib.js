/**
 * Created by Bcass Sawa on 11/27/2018.
 */
$(document).ajaxSend(function (e, xhr, options) {
    var token = $('input[name="_csrf"]').val();
    xhr.setRequestHeader("X-CSRF-TOKEN", token);
    NProgress.start();
}).ajaxStart(function () {
    NProgress.set(0.4);
}).ajaxStop(function () {
    NProgress.done();
}).ajaxError(function () {
    NProgress.done();
}).ajaxComplete(function () {
    NProgress.done();
}).ajaxSuccess(function () {
    NProgress.done();
});

$(document).ajaxError(function (event, jqxhr, settings, thrownError) {
    switch (jqxhr.status) {
        case 500:
            errorMsg("There is an unwanted exception. Please contact with concern developer.");
            break;
        case 404:
            errorMsg("Invalid Request - 404");
            break;
        case 400:
            errorMsg("Bad Request - 400");
            break;
    }
});


function indexRowNo(tableId) {
    let iterator = 0;
    tableId.find('tbody tr').each(function () {
        iterator = iterator + 1;
        let selectedRow = $(this).closest('tr');
        selectedRow.find('.rowNumber').val(iterator);
    });
}

spms = (function () {

    /**
     * validate the date entered
     * @param $this
     * @returns {boolean}
     */
    function isEnteredDateValid($this) {
        var date = $this.val();

        if (date === '') {
            return false;
        }
        var rxDatePattern = /^(\d{1,2})(\/|.)(\d{1,2})(\/|.)(\d{4})$/;
        var dtArray = date.match(rxDatePattern);

        if (dtArray == null) {
            $this.val('');
            $this.focus();
            return false;
        }

        var dtDay = dtArray[1];
        var dtMonth = dtArray[3];
        var dtYear = dtArray[5];

        if (dtMonth < 1 || dtMonth > 12) {
            $this.val('');
            $this.focus();
            return false;
        } else if (dtDay < 1 || dtDay > 31) {
            $this.val('');
            $this.focus();
            return false;
        } else if ((dtMonth === 4 || dtMonth === 6 || dtMonth === 9 || dtMonth === 11) && dtDay === 31) {
            $this.val('');
            $this.focus();
            return false;
            return false;
        } else if (dtMonth === 2) {
            var isleap = (dtYear % 4 === 0 && (dtYear % 100 !== 0 || dtYear % 400 === 0));
            if (dtDay > 29 || (dtDay === 29 && !isleap)) {
                $this.val('');
                $this.focus();
                return false;
            }
        }
        return true;
    }

    function isFormValid(form) {
        $.validator.setDefaults({
            /*highlight: function (element) {
                $(element).closest('.form-group').addClass('has-error');
            },
            unhighlight: function (element) {
                $(element).closest('.form-group').removeClass('has-error');
            },*/
            errorElement: 'span',
            errorClass: 'help-block',
            errorPlacement: function (error, element) {
                if (element.parent('.input-group').length) {
                    error.insertAfter(element.parent());
                } else {
                    error.insertAfter(element);
                }
            }
        });
        form.validate({
            errorElement: 'span',
            errorPlacement: function (error, element) {
                error.addClass('invalid-feedback');
                element.closest('.col-4').append(error);
            },
            highlight: function (element, errorClass, validClass) {
                $(element).addClass('is-invalid');
            },
            unhighlight: function (element, errorClass, validClass) {
                $(element).removeClass('is-invalid');
            }
        });
    }

    function getUrl() {
        return window.location.protocol + '//' + window.location.host + '/bcs/';
        // return window.location.protocol + '//' + window.location.host + '/';
        // return 'http://www.autga.bt/bcs/';
    }

    function baseReportLocation() {
        return window.location.protocol + '//' + window.location.host + '/bcs/resources/reports/';
        // return window.location.protocol + '//' + window.location.host + '/resources/reports/';
    }

    //index the table gridaccProfitAndLossReport
    function _formIndexing(tableBody, row, serialNo, iterator) {
        if (!iterator)
            iterator = 0;

        for (let i = 0; i < row.length; i++) {
            tableBody.children().eq(i).children().children().each(
                function () {
                    if (this.name) {
                        this.name = this.name.replace(
                            /\[(\d+)\]/, function (str, p) {
                                return '[' + (i + iterator) + ']';
                            });
                    }

                    if ($(this).hasClass(serialNo)) {
                        $(this).val(i + 1);
                    }
                }
            );
        }
    }

    function addRowNumber(tableRow) {
        tableRow.each(function (idx) {
            $(this).closest('tr').find('#index').val(idx + 1);
        });
    }

    function loadGridDropDown(element, data) {
        if (!data) {
            data = [];
        }

        if (data.length) {
            element.append(
                $(
                    '<option/>', {
                        value: "",
                        text: "-- Please Select --"
                    }
                )
            );

            $.each(
                data, function (index, itemData) {
                    if (itemData.valueInteger != null) {
                        element.append(
                            $(
                                '<option/>', {
                                    value: itemData.valueInteger,
                                    text: itemData.text
                                }
                            )
                        );
                    }
                    if (itemData.id != null) {
                        element.append(
                            $(
                                '<option/>', {
                                    value: itemData.id,
                                    text: itemData.text
                                }
                            )
                        );
                    }
                    if (itemData.valueBigInteger != null) {
                        element.append(
                            $(
                                '<option/>', {
                                    value: itemData.valueBigInteger,
                                    text: itemData.text
                                }
                            )
                        );
                    }
                    if (itemData.value != null) {
                        element.append(
                            $(
                                '<option/>', {
                                    value: itemData.value,
                                    text: itemData.text
                                }
                            )
                        );
                    }
                });

        }
    }

    function loadDropDown(element, data) {
        element.append(
            $(
                '<option/>', {
                    value: "",
                    text: "-- Please Select --"
                }
            )
        );

        $.each(
            data, function (index, itemData) {
                element.append(
                    $(
                        '<option/>', {
                            value: itemData.value,
                            text: itemData.text
                        }
                    )
                );
            });
    }

    /**
     * final date formatted
     * @param day
     * @param month
     * @param year
     * @returns {string}
     */
    function formattedDate(day, month, year) {

        var monthNames = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

        if (parseInt(month) === 1) {
            month = monthNames[0];
        } else if (parseInt(month) === 2) {
            month = monthNames[1];
        } else if (parseInt(month) === 3) {
            month = monthNames[2];
        } else if (parseInt(month) === 4) {
            month = monthNames[3];
        } else if (parseInt(month) === 5) {
            month = monthNames[4];
        } else if (parseInt(month) === 6) {
            month = monthNames[5];
        } else if (parseInt(month) === 7) {
            month = monthNames[6];
        } else if (parseInt(month) === 8) {
            month = monthNames[7];
        } else if (parseInt(month) === 9) {
            month = monthNames[8];
        } else if (parseInt(month) === 10) {
            month = monthNames[9];
        } else if (parseInt(month) === 11) {
            month = monthNames[10];
        } else if (parseInt(month) === 12) {
            month = monthNames[11];
        }
        return day + '-' + month + '-' + year;
    }

    /**
     * format Amount
     * @param amount
     * @returns {XML|string|void}
     */
    function formatAmount(amount) {
        return amount.replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,');
    }

    function numberWithCommas(x) {
        return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }

    function formatAmountOnKeyPress(amount) {
        return amount.replace(/\D/g, "").replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,');
    }

    /**
     * remove comma
     * @param amount
     * @returns {XML|string|void}
     */
    function removeCommaSeparation(amount) {
        return amount.replace(/,(?=.*\.\d+)/g, '');
    }


    /**
     * Auto search ID Management
     * @type {number}
     */

    let iterator = 1;

    function addRowWithAutoSearchId(tableBody, row, list) {
        let firstRow = tableBody.children('tr:first');
        let lastRow = tableBody.children('tr:last');

        let finalRow = firstRow.clone().insertBefore(lastRow)
            .find('input[type="text"]').val('');

        finalRow.attr('id', 'autoCompleteId' + iterator);

        $('#autoCompleteId' + iterator).devbridgeAutocomplete({
            lookup: list
        });
        iterator = iterator + 1;
        _formIndexing(tableBody, row);
    }

    function loadSearchList(data, id) {

        let items = new Bloodhound({
            datumTokenizer: Bloodhound.tokenizers.obj.whitespace('text'),
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            local: data
        });

        // items.initialize();

        id.typeahead({
                hint: true,
                highlight: true,
            },
            {
                name: 'items',
                displayKey: 'text',
                source: items.ttAdapter(),
                templates: {
                    notFound: '<div>Not Found</div>',
                    pending: '<div>Loading...</div>',
                    suggestion: function (data) {
                        return '<div>' + data.text + '</div>'
                    },
                }
            }).on('typeahead:selected', function (event, data) {
            $('.typeHead').val(data.valueBigInteger);

        });
    }

    function ajax(url, type, data, callback) {
        $.ajax({
            url: url,
            type: type,
            data: data,
            success: callback
        });
    }

    function ajax_with_attachment(url, type, data, callback) {
        $.ajax({
            url: url,
            type: type,
            data: data,
            enctype: 'multipart/form-data',
            contentType: false,
            processData: false,
            success: callback
        });
    }

    /**
     * populate table data
     * @param tableId
     * @param colDef
     * @param data
     */
    function populateTableData(tableId, colDef, data) {
        tableId.DataTable({
            data: data,
            columns: colDef,
            destroy: true,
        });

    }

    function autoSizeInputField(tableTBody) {
        tableTBody.find('tr').each(function (e) {
            let selectedRow = $(this).closest('tr');
            selectedRow.find('.qty').find('.qty-container').css('height',
                selectedRow.find('.description').height() + 'px');

        })
    }

    return {
        _formIndexing: _formIndexing,
        addRowNumber: addRowNumber,
        getUrl: getUrl,
        baseReportLocation: baseReportLocation,
        loadDropDown: loadDropDown,
        loadGridDropDown: loadGridDropDown,
        formattedDate: formattedDate,
        formatAmount: formatAmount,
        ajax: ajax,
        ajax_with_attachment: ajax_with_attachment,
        removeCommaSeparation: removeCommaSeparation,
        formatAmountOnKeyPress: formatAmountOnKeyPress,
        addRowWithAutoSearchId: addRowWithAutoSearchId,
        isFormValid: isFormValid,
        populateTableData: populateTableData,
        loadSearchList: loadSearchList,
        numberWithCommas: numberWithCommas,
        autoSizeInputField: autoSizeInputField
    }
})();

$(document).ready(function () {

    let body = $('body');
    body.on('keypress', '.alphanumeric', function (e) {
            if (allowKeys(e)) {
                return true;
            }
            let regex = new RegExp("^[a-zA-Z0-9]+$");
            let str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
            if (regex.test(str)) {
                return true;
            }
            e.preventDefault();
            return false;
        }
    );

    /**
     * to allow only numeric characters
     * it allow to copy and paste number only characters only
     */
    body.on('keydown', '.numeric', function (e) {
        if (allowKeys(e)) {
            return;
        }
        // Ensure that it is number and stop the key press
        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
            e.preventDefault();
        }
    });

    function allowKeys(e) {
        //Allow: backspace, delete, tab, escape, enter
        return $.inArray(e.keyCode, [46, 8, 9, 27, 13, 110]) !== -1 ||
            //Allow: Ctr+A
            (e.keyCode === 65 && e.ctrlKey === true) ||
            //Allow: home, end, left, right, down, up
            (e.keyCode >= 35 && e.keyCode <= 40);

    }

    /**
     * to allow only decimal numbers
     */
    body.on('keypress', '.decimal', function (event) {
            if ((event.which !== 46 || $(this).val().indexOf('.') !== -1) && (event.which < 48 || event.which > 57)) {
                event.preventDefault();
            }
            let input = $(this).val();
            if ((input.indexOf('.') !== -1) && (input.substring(input.indexOf('.')).length > 1)) {
                event.preventDefault();
            }
        }
    );

    body.on('keypress', '.phone', function (e) {
            let evt = (e) ? e : window.event;
            let charCode = (evt.which) ? evt.which : evt.keyCode;
            return !(charCode !== 43 && charCode > 31 && (charCode < 48 || charCode > 57));

        }
    );

    function isAmount(event, element) {
        let charCode = (event.which) ? event.which : event.keyCode;
        return !((charCode !== 46 || $(element).val().indexOf('.') !== -1) && charCode !== 45 && charCode !== 46 && !(charCode >= 48 && charCode <= 57));
    }

    body.on('keypress', '.amount', function (e) {
            return isAmount(e, this);
        }
    );

    body.on('keydown', '.percentage', function (e) {
            if (allowKeys(e)) {
                return;
            }
            if ((e.which >= 96 && e.which <= 105) || (e.which >= 48 && e.which <= 57)
                || e.which === 190 || e.which === 110) {
            } else {
                e.preventDefault();
            }
        }
    );

    body.on('blur', '.percentage', function (e) {
            let $this = $(this);
            if ($this.val()) {
                let value = $this.val();

                let regex = new RegExp("^[0-9]{1,3}(\\.([0-9]{1,2})?)?$");
                if (!regex.test(value)) {
                    errorMsg('Incorrect Format. Format is ###.##');
                    $this.val('');
                    return;
                }
            }
            if (value > 100) {
                errorMsg('Please insert interest rate between zero(0) and hundred(100)');
                $this.val('');
            }
        }
    );
    //Tapping on touchpad event for auto suggestion
    $(document).on('mousedown', '.autocomplete-suggestion', e => {
        $(e.target).click();
    });

// Catch the keydown for the entire document
    let next;
    body.on('keydown', 'input, select, textarea', function (e) {

        let self = $(this)
            , form = self.parents('form:eq(0)')
            , focusable;

        if (e.keyCode === 13) {
            focusable = form.find('input,a,select,button,textarea').filter(':visible:not([readonly]):enabled');
            next = focusable.eq(focusable.index(this) + 1);
            if (next.length) {
                next.focus();
            } else {
                form.submit();
            }
            return false;
        }
    });

    let tabindex = 1;

    $('input,select').each(function () {
        if (this.type !== "hidden") {
            $(this).attr("tabindex", tabindex);
            tabindex++;
        }
    });


    // $(document).on('focus', '.select2', function (e) {
    //     //s2element.select2('open');
    //     var self = $(this), form = self.parents('form:eq(0)'), focusable;
    //     // Set focus back to select2 element on closing.
    //     if (e.originalEvent) {
    //         var s2element = $(this).siblings('select');
    //         s2element.on('select2:closing', function (e) {
    //             //s2element.select2('focus');
    //             $('select').select2().trigger("select2:close");
    //             //alert(form);
    //             focusable = form.find('input,a,select,button,textarea').filter(':visible:not([readonly]):enabled');
    //             next = focusable.eq(focusable.index(this) + 1);
    //             if (next.length) {
    //                 next.focus();
    //             }
    //             return false;
    //         });
    //     }
    // });


    body.on('paste', '.formatDate', function (e) {
        e.preventDefault();
    });

    body.on('blur', '.formatDate', function (e) {

        if ($(this).val() !== '') {
            // if (e.keyCode == 13) {
            if (isEnteredDateValid($(this))) {

                let dateSplit = $(this).val().split('.');

                let day = dateSplit[0];
                let month = dateSplit[1];
                let year = dateSplit[2];

                //check whether selected date is within a active financial year
                let date = spms.formattedDate(day, month, year);

                const financialYearFrom = new Date($('#financialYearFrom').val());
                const financialYearTo = new Date($('#financialYearTo').val());

                const enteredDate = new Date(date);
                let currentDate = new Date();
                let curMonth = currentDate.getUTCMonth() + 1; //months from 1-12
                let curDay = currentDate.getUTCDate();
                let curYear = currentDate.getUTCFullYear();
                let curDate = new Date(spms.formattedDate(curDay, curMonth, curYear));

                if (curDate < enteredDate) {
                    errorMsg("Future date not allowed.");
                    $(this).val('');
                    return false;
                }

                if (enteredDate >= financialYearFrom && enteredDate <= financialYearTo) {
                    $(this).val(date);
                } else {
                    errorMsg("Invalid date.");
                    $(this).val('');
                }
            }
        }
    });

    let dateFormat2 = $('.dateFormat2');

    dateFormat2.on('paste', function (e) {
        e.preventDefault();
    });
    dateFormat2.on('blur', function (e) {
        if ($(this).val() !== '') {
            // if (e.keyCode == 13) {
            if (isEnteredDateValid($(this))) {
                let dateSplit = $(this).val().split('.');
                let day = dateSplit[0];
                let month = dateSplit[1];
                let year = dateSplit[2];

                //check whether selected date is within a active financial year
                let date = spms.formattedDate(day, month, year);
                $(this).val(date);

            }
        }
    });


    let preDateAllow = $('.preDateAllow');

    preDateAllow.on('paste', function (e) {
        e.preventDefault();
    });

    preDateAllow.on('blur', function (e) {
        if ($(this).val() !== '') {
            // if (e.keyCode == 13) {
            if (isEnteredDateValid($(this))) {
                let dateSplit = $(this).val().split('.');
                let day = dateSplit[0];
                let month = dateSplit[1];
                let year = dateSplit[2];

                //check whether selected date is within a active financial year
                let date = spms.formattedDate(day, month, year);

                const financialYearFrom = new Date($('#financialYearFrom').val());
                const financialYearTo = new Date($('#financialYearTo').val());
                const enteredDate = new Date(date);
                let currentDate = new Date();
                let curMonth = currentDate.getUTCMonth() + 1; //months from 1-12
                let curDay = currentDate.getUTCDate();
                let curYear = currentDate.getUTCFullYear();
                alert(spms.formattedDate(curDay, curMonth, curYear))
                let curDate = new Date(spms.formattedDate(curDay, curMonth, curYear));
                new Date(curDay, curMonth - 1, curYear);
                // if (curDate < enteredDate) {
                //     errorMsg("Future date not allowed.");
                //     $(this).val('');
                //     return false;
                // }
                $(this).val(date);
                if (enteredDate >= financialYearFrom && enteredDate <= financialYearTo) {

                } else {
                    errorMsg("Invalid date.");
                    $(this).val('');
                }
            }
        }
    });

    /**
     * validate the date entered
     * @param $this
     * @returns {boolean}
     */
    function isEnteredDateValid($this) {
        var date = $this.val();

        if (date === '') {
            return false;
        }
        var rxDatePattern = /^(\d{1,2})(\/|.)(\d{1,2})(\/|.)(\d{4})$/;
        var dtArray = date.match(rxDatePattern);

        if (dtArray == null) {
            $this.val('');
            $this.focus();
            return false;
        }

        var dtDay = dtArray[1];
        var dtMonth = dtArray[3];
        var dtYear = dtArray[5];

        if (dtMonth < 1 || dtMonth > 12) {
            $this.val('');
            $this.focus();
            return false;
        } else if (dtDay < 1 || dtDay > 31) {
            $this.val('');
            $this.focus();
            return false;
        } else if ((dtMonth == 4 || dtMonth == 6 || dtMonth == 9 || dtMonth == 11) && dtDay == 31) {
            $this.val('');
            $this.focus();
            return false;
        } else if (dtMonth == 2) {
            var isleap = (dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0));
            if (dtDay > 29 || (dtDay == 29 && !isleap)) {
                $this.val('');
                $this.focus();
                return false;
            }
        }
        return true;
    }

    var datePickerOptions = {
        dateFormat: "dd-M-yy",
        changeMonth: true,
        changeYear: true,
        beforeShow: function (input, inst) {
            if ($(input).prop("readonly")) {
                return false;
            }
        }
    };
    let datePicker = $(".datepicker");
    datePicker.datepicker(datePickerOptions);

    datePicker.keypress(function (e) {
        if (e.keyCode === 8)
            $(this).val('');
        e.preventDefault();
    });

    body.on('focus', '.datepicker', function () {
        if ($(this).hasClass('dynamic')) {
            $(this).datepicker(datePickerOptions);
        }
    });

    //modal overlay problem solves
    $(document).on('show.bs.modal', '.modal', function () {
        const zIndex = 1040 + 10 * $('.modal:visible').length;
        $(this).css('z-index', zIndex);
        setTimeout(() => $('.modal-backdrop')
            .not('.modal-stack').css('z-index', zIndex - 1)
            .addClass('modal-stack'));
    });


    // $(document).on('keydown', function (e) {
    //     // You may replace `c` with whatever key you want
    //     if ((e.metaKey || e.ctrlKey) && (String.fromCharCode(e.which).toLowerCase() === 'c')) {
    //         $('#exampleModal').modal('show');
    //     }
    // });

    // $('body').dbKeypress(17, function (e) {
    //     alert("ff");
    //     if (e.keyCode == 16) {
    //         alert('SHIFT key was double-pressed');
    //
    //     } else if (e.keyCode == 17) {
    //         alert('CTRL key was double-pressed');
    //     }
    // });

    // $(document).on('keypress', function (e) {
    //     switch (e.which) {
    //         case 38:
    //             log("event:UP ARROW key in input.tInput");
    //             focusPrevious($(this).data('index'));
    //             break;
    //         case 40:
    //             log('event:DOWN ARROW key in input.tInput');
    //             focusNext($(this).data('index'));
    //             break;
    //     }
    // });
});


/**
 * format date
 * @param date
 * @returns {string}
 */
function formatAsDate(date) {

    if (date && date.toString().includes('-')) {

        let dateSplit = date.split('-');
        let year = dateSplit[0];
        let month = dateSplit[1];
        let day = dateSplit[2];
        return spms.formattedDate(day, month, year)
    }
}

function errorMsg(msg, callback) {
    swal({
        title: msg,
        text: "Click OK to exit",
        type: "warning"
    }, function (e) {
        if (callback !== undefined)
            callback(e);
    });
}

function successMsg(msg, callback) {
    swal({
        title: msg,
        text: "Click OK to exit",
        type: "success"
    }, function (e) {
        if (callback !== undefined)
            callback(e);
    });
}

function confirmMessage(msg, callback) {
    swal({
        title: msg,
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#dd6b55",
        confirmButtonText: "Confirm",
        closeOnConfirm: false
    }, function (isConfirm) {
        if (isConfirm) {
            callback(isConfirm);
        }
    })
}

/**
 * Responsible to populate the entire form
 * @param {json} data
 * @return {void}
 */
function populate(data) {
    for (let i in data) {
        if (typeof (data[i]) === 'object') {
            //populate(data[i]);
        } else {
            $(
                "input[type='text'][name='" + i + "']," +
                " input[type='hidden'][name='" + i + "'], " +
                "input[type='checkbox'][name='" + i + "'], " +
                "select[name='" + i + "'], textarea[name='" + i + "']"
            )
                .val(data[i]);

            $("input[type='radio'][name='" + i + "'][value='" + data[i] + "']").prop('checked', true);
            if ($("input[name='" + i + "']").hasClass('formatDate')) {
                $("input[name='" + i + "']").val(formatAsDate(data[i]));
            }

            //populate for select
            /* if ($("select[name='" + i + "']").hasClass('select2')) {
                 $("select[name='" + i + "']").select2({
                     data: data[i]
                 })
             }*/
        }
    }


    $('form').find('input[type="checkbox"]').each(
        function () {
            if ($(this).siblings('input[type="hidden"]').val() == "true" ||
                $(this).siblings('input[type="hidden"]').val() == 1) {
                $(this).prop('checked', true);
            } else {
                $(this).prop('checked', false);
            }
        }
    );
}

$.fn.disableElements = function (status) {
    $(this).removeClass('error');
    $(this).each(
        function () {
            $(this).attr('readonly', status);
            $(this).find('option').prop('disabled', status);

            if ($(this).is(':checkbox') || $(this).is(':radio')) {
                $(this).attr('disabled', status);
            }
            $('input:checkbox[name=checkme]').attr('disabled', status);
        }
    );
};





