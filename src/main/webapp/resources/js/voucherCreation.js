/**
 * Created by jigmePc on 5/7/2019.
 */
voucherCreation = (function () {

    let ledgerList = [];
    let ledgerListForContraVoucherType = [];
    let selectedTd;
    let selectedRow;
    let globalSelectedRow;
    let selectedRowIndex;
    let voucherCreationGridTBody = $('#voucherCreationGrid tbody');
    let depreciationGridTBody = $('#depreciationGrid tbody');

    let voucherCreationGrid = $('#voucherCreationGrid').dataTable({
        "paging": false,
        "searching": false,
        "sort": false,
        info: false
    });

    function loadLedgerDropdown() {

        $('#voucherTypeId').on('change', function () {
            let contraOptionVoucherType = parseInt($('#voucherTypeId').val())
            $('#ledgerId1').find('option').remove();
            if (contraOptionVoucherType === 3) {
                getLedgerListForContraVoucherType(1);
            } else {
                _loadGridDropDown(1);
            }
        });
        // _loadGridDropDown(1);

    }

    function _loadGridDropDown(index) {
        $.ajax({
            url: 'voucherCreation/getLedgerList',
            type: 'GET',
            success: function (res) {
                ledgerList = res;
                spms.loadGridDropDown(voucherCreationGridTBody.find('tr').find('#ledgerId' + index), res);
                // loadGridDropDownForVoucher(voucherCreationGridTBody.find('tr').find('#ledgerId' + index), res, 0);
            }
        })
    }


    function getLedgerListForContraVoucherType(index) {

        $.ajax({
            url: 'voucherCreation/getLedgerListForContraVoucherType',
            type: 'GET',
            success: function (res) {
                ledgerListForContraVoucherType = res;
                spms.loadGridDropDown(voucherCreationGridTBody.find('tr').find('#ledgerId' + index), res);
                // loadGridDropDownForVoucher(voucherCreationGridTBody.find('tr').find('#ledgerId' + index), res, 0);
            }
        })
    }


    function btnResetClick() {
        $('#btnReset').on('click', function () {
            window.location = '/voucherCreation';
        })
    }

    $('#voucherCreationGrid tbody').on('keydown', 'td', function (e) {
        if (e.keyCode === 13) {

            selectedRowIndex = ($(this).closest('td').parent()[0].sectionRowIndex) + 1;
            selectedTd = $(this).closest('tr').find('td');
            selectedRow = $(this).closest('tr');
        }
    });

    // $('#voucherCreationGrid tbody').on('keydown', 'td', function (e) {
    //     if (e.keyCode === 13) {
    //         e.preventDefault(); // Prevent form submission or default behavior
    //
    //         var nextInput = $(this).closest('td').next('td').find('input');
    //         if (nextInput.length > 0) {
    //             nextInput.focus();
    //         } else {
    //             // No next input field found, you can handle this case as needed
    //             // For example, move focus to the first input field in the next row
    //             var nextRow = $(this).closest('tr').next('tr');
    //             if (nextRow.length > 0) {
    //                 nextInput = nextRow.find('input').first();
    //                 nextInput.focus();
    //             }
    //         }
    //     }
    // });

    function addRowToGrid() {
        document.body.onkeydown = function (e) {

            if (e.keyCode == 13) {
                let totalCredit = $('#totalCredit');
                let totalDebit = $('#totalDebit');

                let row = voucherCreationGridTBody.find('tr');
                let index = row.length + 1;
                let lastRowIndex = row.length;

                let isDebitOrCredit = row.find('#crOrDr1').val();

                let status = true;
                let rowToBeDeleted = 0;
                if (spms.removeCommaSeparation(totalCredit.val()) != spms.removeCommaSeparation(totalDebit.val())) {
                    if (spms.removeCommaSeparation(totalDebit.val()) == getCrTotal(selectedRowIndex)) {
                        while (lastRowIndex > selectedRowIndex) {
                            selectedRowIndex = selectedRowIndex + 1;
                            if (parseInt(row.find('#crOrDr' + selectedRowIndex).val()) === 0) {
                                if (status) {
                                    rowToBeDeleted = selectedRowIndex;
                                    status = false;
                                }
                                document.getElementById('voucherCreationGrid').deleteRow(rowToBeDeleted);
                            }
                        }
                    } else if (spms.removeCommaSeparation(totalCredit.val()) == getDrTotal(selectedRowIndex)) {
                        while (lastRowIndex > selectedRowIndex) {
                            selectedRowIndex = selectedRowIndex + 1;
                            if (parseInt(row.find('#crOrDr' + selectedRowIndex).val()) === 1) {
                                if (status) {
                                    rowToBeDeleted = selectedRowIndex;
                                    status = false;
                                }
                                document.getElementById('voucherCreationGrid').deleteRow(rowToBeDeleted);
                            }
                        }
                    } else {
                        if ((selectedTd.find('.ledgerId').val()) && (selectedTd.find('.debitAmount').val() || selectedTd.find('.creditAmount').val())) {

                            function onFocusInputJump(){
                                let newRowIndex = row.length + 1;
                                let inputField = selectedTd.find('.ledgerId');
                                if (parseInt(isDebitOrCredit) === 1) {
                                    inputField = selectedTd.find('.debitAmount');
                                } else {
                                    inputField = selectedTd.find('.creditAmount');
                                }
                                inputField.focus();
                            }
                            onFocusInputJump();


                            _addRow(voucherCreationGridTBody, row, index);
                        }
                    }

                    if (parseInt(isDebitOrCredit) === 1) {
                        _checkItsDrOrCr(0, index);
                        voucherCreationGridTBody.find('td').find('#crOrDr' + index).val(0);
                        let creditAmount = spms.removeCommaSeparation(totalDebit.val()) - spms.removeCommaSeparation(totalCredit.val());
                        $('#creditAmount' + index).val(spms.formatAmount(creditAmount.toFixed(2)));
                        if (selectedTd.find('.debitAmount').val() !== '') {
                            selectedTd.find('.debitAmount').val(spms.formatAmount((parseFloat(spms.removeCommaSeparation(selectedTd.find('.debitAmount').val()))).toFixed(2)));
                        }
                        _calculateTotalCr();

                    } else {
                        _checkItsDrOrCr(1, index);
                        voucherCreationGridTBody.find('tr').find('td').find('#crOrDr' + index).val(1);
                        let debitAmount = spms.removeCommaSeparation(totalCredit.val()) - spms.removeCommaSeparation(totalDebit.val());
                        $('#debitAmount' + index).val(spms.formatAmount(debitAmount.toFixed(2)));
                        if (selectedTd.find('.creditAmount').val() !== '') {
                            selectedTd.find('.creditAmount').val(spms.formatAmount((parseFloat(spms.removeCommaSeparation(selectedTd.find('.creditAmount').val()))).toFixed(2)));
                        }
                        _calculateTotalDr();
                    }
                }
            }
        }
    }

    function getCrTotal(selectedRowIndex) {
        var totalCr = 0, iterator = 0;
        $('#voucherCreationGrid tbody tr').each(function () {
            var selectedRow = $(this).closest('tr');
            if (selectedRow.find(".creditAmount").val() != '') {
                if (iterator < selectedRowIndex) {
                    totalCr = totalCr + parseFloat(spms.removeCommaSeparation(selectedRow.find(".creditAmount").val()));
                }
            }
            iterator = iterator + 1;
        });
        return totalCr;
    }

    function getDrTotal(selectedRowIndex) {
        let totalDr = 0, iterator = 0;
        $('#voucherCreationGrid tbody tr').each(function () {
            let selectedRow = $(this).closest('tr');
            if (selectedRow.find(".debitAmount").val() !== '') {
                if (iterator < selectedRowIndex) {
                    totalDr = totalDr + parseFloat(spms.removeCommaSeparation(selectedRow.find(".debitAmount").val()));
                }
            }
            iterator = iterator + 1;
        });
        return totalDr;
    }

    function _addRow(tableBody, row, index) {
        var row = '<tr>\
            <td><select class="form-control  form-control-sm voucherDetail crOrDr" id="crOrDr' + index + '"><option value="1">Dr</option> <option value="0">Cr</option></select></td> \
            <td><select  name="voucherDetailDTOList[0].ledgerId" class="form-control  form-control-sm voucherDetail ledgerId required"  id="ledgerId' + index + '" > </select></td> \
            <td><input  type="text" name="voucherDetailDTOList[0].debitAmount"  class="form-control  form-control-sm right-align voucherDetail debitAmount"  id="debitAmount' + index + '"><input type="hidden" name="voucherDetailDTOList[0].voucherDetailId"  class="form-control voucherDetailId"  id="voucherDetailId' + index + '"></td> \
            <td><input type="text" name="voucherDetailDTOList[0].creditAmount" class="form-control   form-control-sm  right-align voucherDetail creditAmount" id="creditAmount' + index + '" ></td> \
            </tr>';

        tableBody.append(row);
        var allRow = tableBody.find('tr');
        spms._formIndexing(tableBody, allRow);
        voucherCreationGridTBody.find('tr').find('#ledgerId' + index).select2();
        // _loadGridDropDown(index);


        let contraOptionVoucherType = parseInt($('#voucherTypeId').val())

        $($('#ledgerId2').target).find('option').remove();


        if (contraOptionVoucherType === 3) {

            getLedgerListForContraVoucherType(index);

        } else {
            _loadGridDropDown(index);
        }


    }


    function onChangeVoucherType() {
        $('#voucherTypeId').on('change', function () {
            let contraOptionVoucherType = parseInt($('#voucherTypeId').val())

            // if (contraOptionVoucherType === 3) {
            //     $('#voucherCreationGrid tbody').find('option').remove();
            //     getLedgerListForContraVoucherType();
            //     console.log(ledgerListForContraVoucherType)
            //
            //     loadGridDropDownForVoucher(voucherCreationGridTBody.find('tr').find('#ledgerId' + 1), ledgerListForContraVoucherType, 3);
            //
            // } else {
            //     $('#voucherCreationGrid tbody').find('option').remove();
            //     loadGridDropDownForVoucher(voucherCreationGridTBody.find('tr').find('#ledgerId' + 1), ledgerList, 0);
            // }


            // ledgerList.length=0;

            // console.log(ledgerList)


            var row = voucherCreationGridTBody.find('tr');
            voucherCreationGridTBody.find('tr:gt(0)').remove();


            var index = row.length;
            if ($('#voucherTypeId').val() != '') {

                $('.voucherDetail').attr('disabled', false);


            } else {
                $('.voucherDetail').attr('disabled', true);
            }
            if ($('#voucherTypeId').val() == 1 || $('#voucherTypeId').val() == 4) {
                _checkItsDrOrCr(1, row.length);
                row.find('#crOrDr').attr('disabled', true);
                row.find('.crOrDr').val(1);
            } else {
                _checkItsDrOrCr(0, row.length)
                row.find('.crOrDr').val(0);
                row.find('.crOrDr').attr('disabled', true);
            }


        })
    }

    function onChangeLedgerIdType() {
        voucherCreationGridTBody.on('change', '.ledgerId', function () {
            var selectedRow = $(this).closest('tr');
            $.ajax({
                url: 'voucherCreation/checkAccountHeadType',
                type: 'GET',
                data: {ledgerId: selectedRow.find('.ledgerId').val()},
                success: function (res) {
                    if (res) {
                        $('#depreciationShow').attr('hidden', false);
                        globalSelectedRow = selectedRow;
                        /* $.ajax({
                             url: 'voucherCreation/getParticularList',
                             type: 'GET',
                             success: function (res) {
                                 itemNameList = res;
                                 spms.loadGridDropDown(depreciationGridTBody.find('tr').find('#depreciationId'), res);
                             }
                         })*/
                        depreciationGridTBody.find('tr').find('td').find('.dateOfPurchase').focus();
                    } else {
                        $('#depreciationShow').attr('hidden', true);

                    }
                }
            })
        })
    }

    /*function onChangeParticularIdType() {
        depreciationGridTBody.on('change', '.depreciationId', function () {
            var selectedRow = $(this).closest('tr');
            $.ajax({
                url: 'voucherCreation/getRateOfDepreciation',
                type: 'GET',
                data: {particularId: selectedRow.find('.depreciationId').val()},
                success: function (res) {
                    if (res) {
                        selectedRow.find('.rateOfDepreciation').val(res)
                    }
                }
            })
        })
    }*/

    depreciationGridTBody.on('keyup', '.cost', function () {
        calculateTotalDepreciationAmount();
    });

    depreciationGridTBody.on('keyup', '.qty', function () {
        calculateTotalDepreciationAmount()
    });

    depreciationGridTBody.on('keydown', '.dateOfPurchase ', function (e) {
        var selectedRow = $(this).closest('tr');
        if ($(this).val() !== '') {
            if (e.keyCode === 13) {
                var dateSplit = $(this).val().split('.');
                var day = dateSplit[0];
                var month = dateSplit[1];
                var year = dateSplit[2];
                selectedRow.find('td').find('.dateOfPurchase').val(spms.formattedDate(day, month, year));
            }
        }
    });

    function calculateTotalDepreciationAmount() {
        var totalAmount = 0;
        depreciationGridTBody.find('tr').each(function () {
            var row = $(this).closest('tr');
            if (typeof row.find('.cost').val() != 'undefined' && typeof row.find('.qty').val() != 'undefined')
                totalAmount = totalAmount + (row.find('.cost').val() == '' ? 1 : parseInt(row.find('.cost').val())) *
                    (row.find('.qty').val() === '' ? 1 : parseInt(row.find('.qty').val()));
        });
        if (globalSelectedRow.find('.crOrDr').val() === 1) {
            globalSelectedRow.find('td').find('.debitAmount').val(totalAmount.toFixed(2));
            _calculateTotalDr()
        } else {
            globalSelectedRow.find('td').find('.creditAmount').val(totalAmount.toFixed(2));
            _calculateTotalCr()

        }
    }

    function save() {
        $('.globalForm').validate({
            submitHandler: function (form) {
                if (isValidForSave()) {
                    swal({
                        title: "Confirmation",
                        text: "Are you sure that you want to proceed ?",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "Confirm",
                        cancelButtonText: "Cancel",
                        closeOnConfirm: false,
                        closeOnCancel: false
                    }, function (isConfirm) {
                        if (isConfirm) {
                            var data = $(form).serializeArray();
                            $.ajax({
                                url: 'voucherCreation/save',
                                type: 'POST',
                                data: data,
                                success: function (res) {
                                    $('#btnSave').attr('disabled', false);
                                    if (res.status == 1) {
                                        swal({
                                            timer: 800,
                                            type: "success",
                                            title: res.text,
                                            showConfirmButton: false
                                        }, function () {

                                            window.location = spms.getUrl() + 'payment/generateReport?voucherNo=' +
                                                encodeURIComponent(res.dto.voucherNo) + '&type=' + $('#voucherTypeId').val();

                                            $('#voucherTypeId').val('');
                                            $('#narration').val('');
                                        });

                                    } else {
                                        swal({
                                            title: res.text,
                                            text: "Click OK to exit",
                                            type: "warning"
                                        }, function () {
                                        });
                                    }
                                }
                            });
                        } else {
                            swal("Cancelled", "You have cancelled the operation.", "warning");
                        }
                    });
                }
            }
        });
    }

    function isValidForSave() {
        var status = true;
        $('#voucherCreationGrid').find('tr').each(function () {
            if (typeof $(this).find('.ledgerId').val() != 'undefined') {
                if ($(this).find('.ledgerId').val() == "") {
                    status = false;
                } else if ($(this).find('.crOrDr').val() == 1) {
                    if ($(this).find('.debitAmount').val() == '') {
                        status = false;
                    }
                } else if ($(this).find('.crOrDr').val() == 0) {
                    if ($(this).find('.creditAmount').val() == '') {
                        status = false;
                    }
                }
            }
        });
        return status;
    }

    function onChangeDrOrCr() {
        $('#voucherCreationGrid tbody').on('change', '.crOrDr', function () {
            let selectedRow = $(this).closest('tr');
            if (parseInt(selectedRow.find('.crOrDr').val()) === 1) {

                selectedRow.find('td').find('.debitAmount').val('');
                selectedRow.find('td').find('.creditAmount').val('');
                selectedRow.find('td').find('.debitAmount').attr('disabled', false);
                selectedRow.find('td').find('.creditAmount').attr('disabled', true);
                _calculateTotalCr();

            } else {
                selectedRow.find('td').find('.debitAmount').val('');
                selectedRow.find('td').find('.creditAmount').val('');
                selectedRow.find('td').find('.debitAmount').attr('disabled', true);
                selectedRow.find('td').find('.creditAmount').attr('disabled', false);
                _calculateTotalDr();
            }
        });
    }

    $('#voucherCreationGrid tbody').on('change', '.ledgerId', function () {

        var rowIndex = $(this).closest('td').parent()[0].sectionRowIndex +1;
        voucherCreationGridTBody.find('tr').find('#ledgerId' + rowIndex).removeClass('error');
    });

    function _checkItsDrOrCr(isDebit, index) {

        var debitAmountId = $('#debitAmount' + index);
        var creditAmountId = $('#creditAmount' + index);

        if (isDebit === 1) {
            debitAmountId.val('');
            creditAmountId.val('');
            debitAmountId.attr('disabled', false);
            creditAmountId.attr('disabled', true);
            debitAmountId.addClass('required');
            creditAmountId.removeClass('required');
        } else {
            debitAmountId.val('');
            creditAmountId.val('');
            debitAmountId.attr('disabled', true);
            creditAmountId.attr('disabled', false);
            debitAmountId.removeClass('required');
            creditAmountId.addClass('required');
        }

    }

    function depreciationAddRowToGrid() {
        depreciationGridTBody.on('click', '.btnAddRow', function () {
            var row = $('#depreciationGrid tbody tr');
            var lastRow = $(this).parent('td').parent('tr').prev('tr');
            if ((lastRow.find('.dateOfPurchase').val()) && (lastRow.find('.itemName').val()) &&
                (lastRow.find('.cost').val()) && (lastRow.find('.rateOfDepreciation').val())) {
                _depreciationAddRow(depreciationGridTBody, row);
                lastRow = $(this).parent('td').parent('tr').prev('tr');
                lastRow.find('.id').val('')
            }

            var allRow = depreciationGridTBody.find('tr');
            spms._formIndexing(depreciationGridTBody, allRow);

        });
    }

    function _depreciationAddRow(tableBody, row) {
        var firstRow = tableBody.children('tr:first');
        var lastRow = tableBody.children('tr:last');
        firstRow.clone().insertBefore(lastRow).find('input[type="text"]').val('');
        spms._formIndexing(tableBody, row);
    }

    function onDrOrCrValueEnter() {
        $('#voucherCreationGrid tbody').on('keyup change', '.debitAmount', function () {
            _calculateTotalDr();
        });
        $('#voucherCreationGrid tbody').on('keyup change', '.creditAmount ', function () {
            _calculateTotalCr();
        });

    }


    function _calculateTotalDr() {
        var totalDr = 0;
        $('#totalDebit').val(0);
        $('#voucherCreationGrid tbody tr').each(function () {
            var selectedRow = $(this).closest('tr');
            if (selectedRow.find(".debitAmount").val() !== '') {
                totalDr = totalDr + parseFloat(spms.removeCommaSeparation(selectedRow.find(".debitAmount").val()));
                $('#totalDebit').val(spms.formatAmount(totalDr.toFixed(2)));
            }
        })
    }

    function _calculateTotalCr() {
        $('#totalCredit').val(0);
        var totalCr = 0;
        $('#voucherCreationGrid tbody').find('tr').each(function () {
            var selectedRow = $(this).closest('tr');
            if (selectedRow.find(".creditAmount").val() !== '') {
                totalCr = totalCr + parseFloat(spms.removeCommaSeparation(selectedRow.find(".creditAmount").val()));
                $('#totalCredit').val(spms.formatAmount(totalCr.toFixed(2)));
            }

        })
    }


    if (typeof location.search.split('voucherNo=')[1] != 'undefined') {
        var splitURL = location.search.split('voucherNo=')[1];
        var voucherNo = splitURL.split('&&')[0];
        var voucherTypeId = splitURL.split('&&')[1].split('voucherTypeId=')[1];
        $('#voucherNo').val(voucherNo);
    }

    function getVoucherDetailsByVoucherNo() {
        $.ajax({
            url: 'voucherCreation/getVoucherDetailsByVoucherNo',
            type: 'GET',
            async: false,
            data: {voucherNo: voucherNo, voucherTypeId: voucherTypeId},
            success: function (res) {


                if (res.length != 0) {

                    voucherCreationGrid.dataTable().fnClearTable();
                    $('#voucherTypeId').val(res[0].voucherTypeId).trigger('change');
                    $('#voucherEntryDate').val(formatAsDate(res[0].voucherEntryDate));

                    var iterator = 1;
                    for (var index in res) {

                        voucherCreationGrid.fnAddData(
                            [
                                '<td><select tabindex="3" class="form-control voucherDetail crOrDr" id="crOrDr' + iterator + '"><option value=""></option> <option value="1">Dr</option> <option value="0">Cr</option></select></td> ',
                                '<td><select tabindex="3" name="voucherDetailDTOList[' + index + '].ledgerId" class="form-control ledgerId"  id="ledgerId' + iterator + '" > </select></td>',
                                '<td><input tabindex="3" type="text" name="voucherDetailDTOList[' + index + '].debitAmount"  class="form-control right-align  debitAmount"  id="debitAmount' + iterator + '"  value="' + /*spms.formatAmount(parseInt(*/res[index].debitAmount/*).toFixed(2))*/ + '"><input type="hidden" name="voucherDetailDTOList[' + index + '].voucherDetailId"  class="form-control right-align  voucherDetailId"  id="voucherDetailId' + iterator + '"  value="' + res[index].voucherDetailId + '"></td>',
                                '<td><input tabindex="3"  type="text" name="voucherDetailDTOList[' + index + '].creditAmount" class="form-control   right-align  creditAmount" id="creditAmount' + iterator + '"  value="' + /*spms.formatAmount(parseInt(*/res[index].creditAmount/*).toFixed(2))*/ + '"></td>',
                                '<td><input tabindex="3" type="button" class="btn btn-primary btn-xs btnRemoveRow" id="btnRemove" value="Remove"></td>'
                            ]
                        );

                        if (res[index].debitAmount != null) {
                            voucherCreationGridTBody.find('tr').find('#crOrDr' + iterator).val(1);
                            voucherCreationGridTBody.find('tr').find('#creditAmount' + iterator).val("");
                            voucherCreationGridTBody.find('tr').find('#creditAmount' + iterator).attr("disabled", 'true');
                        } else {
                            voucherCreationGridTBody.find('tr').find('#crOrDr' + iterator).val(0);
                            voucherCreationGridTBody.find('tr').find('#debitAmount' + iterator).val("");
                            voucherCreationGridTBody.find('tr').find('#debitAmount' + iterator).attr("disabled", 'true');
                        }

                        spms.loadGridDropDown(voucherCreationGridTBody.find('tr').find('#ledgerId' + iterator), ledgerList);
                        voucherCreationGridTBody.find('tr').find('#ledgerId' + iterator).val(res[index].ledgerId);
                        voucherCreationGridTBody.find('tr').find('#ledgerId' + iterator).select2();
                        iterator = iterator + 1;
                    }

                }
                _calculateTotalDr();
                _calculateTotalCr();
            }
        })
    }

    function getVoucherNo() {
        $('#voucherTypeId').on('change', function () {
            $.ajax({
                url: 'voucherCreation/getVoucherNo',
                type: 'GET',
                data: {voucherTypeId: $(this).val()},
                success: function (res) {
                    $('#voucherNo').val(res);

                }
            })
        })
    }

    return {
        loadLedgerDropdown: loadLedgerDropdown,
        addRowToGrid: addRowToGrid,
        getVoucherDetailsByVoucherNo: getVoucherDetailsByVoucherNo,
        onChangeVoucherType: onChangeVoucherType,
        save: save,
        onChangeDrOrCr: onChangeDrOrCr,
        onDrOrCrValueEnter: onDrOrCrValueEnter,
        btnResetClick: btnResetClick,
        getVoucherNo: getVoucherNo,
        depreciationAddRowToGrid: depreciationAddRowToGrid,
        // getLedgerListForContraVoucherType: getLedgerListForContraVoucherType,
        // onChangeLedgerIdType: onChangeLedgerIdType
        //onChangeParticularIdType: onChangeParticularIdType
    }
})
();

$(document).ready(function () {
    voucherCreation.loadLedgerDropdown();
    voucherCreation.addRowToGrid();
    voucherCreation.getVoucherDetailsByVoucherNo();
    voucherCreation.onChangeVoucherType();
    voucherCreation.save();
    voucherCreation.onChangeDrOrCr();
    voucherCreation.onDrOrCrValueEnter();
    voucherCreation.btnResetClick();
    voucherCreation.getVoucherNo();
    voucherCreation.depreciationAddRowToGrid();

    // voucherCreation.onChangeLedgerIdType();
    //voucherCreation.onChangeParticularIdType();


});
