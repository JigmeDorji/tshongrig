saleItem = (function () {

        let saleItemGrid = $('#saleItemGrid').dataTable({
            info: false,
            bSort: false,
            autoWidth: false,
            paging: false,
            sorting: false,
            searching: false
        });
        let saleItemGridTBody = $('#saleItemGrid tbody');

        function getItemDetails() {

            $('#itemCode').on('keyup', function () {
                if ($(this).val() !== '') {
                    fetchDetail($(this).val());
                }
            });
        }

        function fetchDetail(itemCode) {
            $.ajax({
                url: 'saleItem/getItemDetails',
                type: 'GET',
                data: {itemCode: itemCode},
                success: function (res) {
                    if (res.length > 0) {
                        $('#itemName').val(res[0].itemName);
                        $('#sellingPrice').val(res[0].sellingPrice);
                        $('#unitName').val(res[0].unitName);
                        $('#returnItem').attr('disabled', false);
                    } else {
                        $('#itemName').val('');
                        $('#sellingPrice').val('');
                    }
                }
            })
        }

        let i = 1;

        function addGridToDetails() {
            let qty = $('#qty');
            let unitName = $('#unitName');
            let saleDate = $('#saleDate');
            let itemCode = $('#itemCode');
            let itemName = $("#itemName");
            let sellingPrice = $('#sellingPrice');
            let totalAmount = parseInt(sellingPrice.val()) * parseInt(qty.val());
            if (!fieldValidation(qty, itemCode, itemName, sellingPrice, saleDate)) {

                $("#counterOrSupply").select2({containerCssClass: ""});
                qty.removeClass('error');
                unitName.removeClass('error');
                itemCode.removeClass('error');
                itemName.removeClass('error');
                sellingPrice.removeClass('error');
                saleDate.removeClass('error');

                var row = "<tr>" +
                    "<td><input type='text' id='index' readonly class='form-control form-control-sm' value='" + i + "'></td>" +
                    "<td><input type='text' id='itemCode' readonly name='saleItemListDTO[" + i + "].itemCode' class='form-control form-control-sm' value='" + itemCode.val().toUpperCase() + "'></td>" +
                    "<td><input type='text' readonly name='saleItemListDTO[" + i + "].locationId' class='form-control form-control-sm' value='" + itemName.val() + "'></td>" +
                    "<td><input type='text' id='unitName' readonly class='form-control form-control-sm right-align'  value=" + unitName.val() + " ></td>" +
                    "<td><input type='text' readonly name='saleItemListDTO[" + i + "].sellingPrice' class='form-control form-control-sm sellingPrice right-align' value='" + sellingPrice.val() + "'></td>" +
                    "<td><input type='hidden' id='initialQty' readonly class='form-control form-control-sm initialQty right-align'><input type='text' id='qty' readonly class='form-control form-control-sm qty right-align amount'   name='saleItemListDTO[" + i + "].qty' value=" + qty.val() + " ></td>" +
                    "<td><input type='text' readonly class='form-control form-control-sm totalAmount right-align totalAmount' id='totalAmount'  name='saleItemListDTO[" + i + "].totalAmount' value=" + totalAmount + " ></td>" +
                    "<td><input type='button'  id='itemEditBtn' class='btn btn-sm btn-primary btn-xs fa fa-trash' value='Edit'><input type='button'  id='btnDeleteItem' class='btn btn-danger btn-sm fa fa-trash' value='Delete'></td>" +
                    "</tr>";
                i++;
                let tableGrid = $('#saleItemGrid');
                let tableBody = tableGrid.find('tbody');
                if (!isValid(tableBody, itemCode)) {
                    var noMatch = $(tableBody).find('td').first().html();
                    if (noMatch == 'No data available in table') {
                        $(tableBody).find('tr').remove();
                    }
                    tableGrid.find('tbody').append(row);

                    var allRow = tableBody.find('tr');
                    spms._formIndexing(tableBody, allRow);
                    if ($('#voucherNo').val() === '') {
                        $('#discountRate').val(0);
                        $('#amtReceived').val(0);
                    } else {
                        $('.resetField').val('');
                    }
                    $('#printBtn').attr('disabled', false)
                }

                $('#itemCode').val('');
                $('#itemName').val('');
                $('#sellingPrice').val('');
                $('#qty').val('');
                calculateTotal();
                spms.addRowNumber($('#saleItemGrid tbody tr'))
            }
        }

        function isValid(tableData, itemCode) {
            var isItemCodeExist = false;
            tableData.find('tr').each(function () {
                if ($(this).find('.itemCode').val() === itemCode.val().toUpperCase()) {
                    swal({
                        title: "This item is already added in the list. Please check.",
                        text: "Click OK to exit",
                        type: "warning"
                    });
                    $('.resetField').val('');
                    isItemCodeExist = true;
                    return false;
                }
            });
            return isItemCodeExist;
        }

        function fieldValidation(qty, itemCode, itemName, sellingPrice, saleDate) {
            let errorExists = false;

            if (checkItemCodeAlreadyExists(itemCode)) {
                errorMsg("This item already exists in the grid.")
                errorExists = true;
            }

            if ($('#counterOrSupply').val() === '') {
                $("#counterOrSupply").select2({containerCssClass: "error"});
                errorExists = true;
            }
            if (qty.val() === '') {
                qty.addClass('error');
                errorExists = true;
            }

            if (itemCode.val() === '') {
                itemCode.addClass('error');
                errorExists = true;
            }
            if (itemName.val() === '') {
                itemName.addClass('error');
                errorExists = true;
            }

            if (sellingPrice.val() === '') {
                sellingPrice.addClass('error');
                errorExists = true;
            }
            if (saleDate.val() === '') {
                saleDate.addClass('error');
                errorExists = true;
            }
            return errorExists;
        }

        function checkItemCodeAlreadyExists(itemCode) {
            let exists = false;
            $('#saleItemGrid').find('tr').each(function () {
                if ($(this).closest('tr').find('#itemCode').val() === itemCode.val().toUpperCase()) {
                    exists = true;
                }
            });
            return exists;
        }

        function generateReceipt() {
            $('#printBtn').on('click', function () {
                if (validateRequiredField()) {
                    $.ajax({
                        url: 'saleItem/saveItemDetails',
                        type: 'POST',
                        data: $('#saleItemForm').serializeArray(),
                        success: function (res) {
                            let receiptNo = res;
                            if (res !== "") {
                                $('#saleItemGrid tbody tr').remove();
                                $.ajax({
                                    url: 'saleItem/generateReceipt',
                                    type: 'GET',
                                    data: {receiptMemoNo: res},
                                    success: function (res) {
                                        printHTML(res, receiptNo);
                                    }
                                });
                                $('#printBtn').attr('disabled', true)
                            }
                        }
                    });
                }
            })
        }

        function validateRequiredField() {

            let validForm = true;
            let isCash = $('#isCash');
            let amtReceived = $('#amtReceived');
            let amtReturn = $('#amtReturn');
            let partyName = $('#partyName');
            let partyAddress = $('#partyAddress');
            let partyContactNo = $('#partyContactNo');
            let bankLedgerId = $('#bankLedgerId');
            let amountReceivedInBank = $('#amountReceivedInBank');

            if (parseFloat($('#grandTotal').val()) > 0) {

                //check sale in type
                if (isCash.val() === '') {
                    errorMsg('Please select sale in type.');
                    isCash.addClass('error');
                    validForm = false;
                } else {
                    isCash.removeClass('error');
                }

                //supply or counter
                if (parseInt($('#counterOrSupply').val()) === 2) {
                    // if (isCash.val() !== 3) {
                    //     errorMsg('It is credit sale. Please check');
                    //     isCash.addClass('error');
                    //     validForm = false;
                    // }
                }

                //validate on cash selection
                if (parseInt(isCash.val()) === 1) {
                    if (amtReturn.val() < 0) {
                        if (partyName.val() === '' || partyAddress.val() === '' || partyContactNo.val() === '') {
                            errorMsg('Please enter party detail.');
                            validForm = false;
                        }
                    }
                }

                //validate on bank selection
                if (parseInt(isCash.val()) === 2) {
                    if (amtReceived.val() === '' || amtReceived.val() <= 0) {
                        errorMsg('Please enter amount received.');
                        amtReceived.addClass('error');
                        validForm = false;
                    } else if (bankLedgerId.val() === '') {
                        errorMsg('Please select bank account.');
                        bankLedgerId.addClass('error');
                        validForm = false;
                    } else if (amtReturn.val() < 0) {
                        if (partyName.val() === '' || partyAddress.val() === '' || partyContactNo.val() === '') {
                            errorMsg('Please enter party detail.');
                            validForm = false;
                        }
                    }
                    amtReceived.removeClass('error');
                    bankLedgerId.removeClass('error');
                }

                //validate on credit selection
                if (parseInt(isCash.val()) === 3) {
                    if (partyName.val() === '' || partyAddress.val() === '' || partyContactNo.val() === '') {
                        errorMsg('Please enter party detail.');
                        validForm = false;
                    }
                }

                //validate on cash and bank selection
                if (parseInt(isCash.val()) === 4) {
                    if (amtReceived.val() === '' || amtReceived.val() <= 0) {
                        errorMsg('Please enter amount received in cash.');
                        amtReceived.addClass('error');
                        validForm = false;
                    } else if (bankLedgerId.val() === '') {
                        errorMsg('Please select bank account.');
                        bankLedgerId.addClass('error');
                        amtReceived.removeClass('error');
                        validForm = false;
                    } else if (amountReceivedInBank.val() <= 0 || amountReceivedInBank.val() === '') {
                        errorMsg('Please enter amount received in bank.');
                        bankLedgerId.removeClass('error');
                        validForm = false;
                    }
                }
            }

            return validForm;
        }

        function printHTML(res, receiptNo) {
            var row = '';
            var total = 0;
            for (var i in res) {
                row += '<tr class="service">' +
                    '<td  class="tableitem" style="font-size:12px;word-wrap:break-word;text-align:left">' + res[i].itemName + '</td>' +
                    '<td  class="tableitem" style="font-size:12px;word-wrap:break-word;text-align:center">' + res[i].qty + '</td>' +
                    '<td  class="tableitem" style="font-size:12px;word-wrap:break-word;text-align:right">' + res[i].sellingPrice.toFixed(2) + '</td>' +
                    '<td  class="tableitem" style="font-size:12px;word-wrap:break-word;text-align:right">' + res[i].totalAmount.toFixed(2) + '</td>' +
                    '</tr>';
                total = total + res[i].totalAmount;
            }
            let companyName = $('#companyName').val();
            let companyAdd = $('#companyAdd').val();
            let contact = $('#contact').val();
            let email = $('#email').val();
            let saleDate = $('#saleDate').val();
            let htmlRawData =
                '<div id="invoice-POS">' +
                '<div style="font-size:12px;word-wrap:break-word;text-align:right">' + saleDate + '</div>' +
                '<div style="font-size:12px;word-wrap:break-word;text-align:right">' + "Sales Receipt #" + receiptNo + '</div>' +
                '<center id="top">' +
                '<div class="info">' +
                '<strong>' + companyName + '</strong>' +
                '<p>' + "Address : " + companyAdd + '</br>' +
                "Contact : " + contact + '</br>' +
                "Email : " + email + '</p>' +
                '</div>' +
                '</center>' +
                '<table style="table-layout:fixed;width:280px">' +
                '<tr class="tabletitle">' +
                '<th width="120px" style="font-size:12px;word-wrap:break-word;text-align:left">Particular</th>' +
                '<th width="40px"style="font-size:12px;word-wrap:break-word;text-align:center">Qty</th>' +
                '<th width="40px" style="font-size:12px;word-wrap:break-word;text-align:right">Rate</th>' +
                '<th width="80px" style="font-size:12px;word-wrap:break-word;text-align:right">Amount</th>' +
                '</tr><hr>' + row +
                '<tr class="tabletitle">' +
                '<td></td>' +
                '<td></td>' +
                '<td style="font-size:12px;word-wrap:break-word;text-align:right"><b>Total:</b></td>' +
                '<td style="font-size:12px;word-wrap:break-word;text-align:right"><b>' + "Nu." + total.toFixed(2) + '</b></td>' +
                '</tr>' +
                '</table>' +
                '<hr></div></br>' +
                '<div id="legalcopy"><center><h5>***Please visit us again***</h5></center></div>';

            //Get the HTML of div
            //var divElements = document.getElementById(divID).innerHTML;
            //Get the HTML of whole page
            var oldPage = document.body.innerHTML;

            //Reset the page's HTML with div's HTML only
            document.body.innerHTML =
                "<html><head><title></title></head><body>" +
                htmlRawData + "</body>";

            //Print Page
            window.print();
            document.body.innerHTML = oldPage;

            setTimeout(function () {
                window.location.reload();
            }, 1000);
        }

        function onBtnClickDeleteItem() {
            $('#saleItemGrid').find('tbody').on('click', 'tr #btnDeleteItem', function () {
                let selectedRow = $(this).closest('tr').addClass('selected');
                selectedRow.remove();
                let allRow = saleItemGridTBody.find('tr');
                spms._formIndexing(saleItemGridTBody, allRow);
                calculateTotal();
            })
        }

        function onBtnClickEditItem() {
            $('#saleItemGrid').find('tbody').on('click', 'tr #itemEditBtn', function () {
                let selectedRow = $(this).closest('tr').addClass('selected');
                selectedRow.find('.qty').focus();
                selectedRow.find('.qty').attr('readonly', false);
                selectedRow.find('.discount').attr('readonly', false);
            })
        }

        function validateQty() {
            $('#saleItemGrid').find('tbody').on('keyup', 'tr #qty', function () {
                let selectedRow = $(this).closest('tr').addClass('selected');
                checkAvailabilityOfItem(selectedRow.find('#itemCode').val(),
                    selectedRow.find('.qty').val(), selectedRow);
                $('#amtReceived').val(0);
                $('#invoiceNo').val('');
                $('#amtReturn').val(0);
                resetPartRelated();
                $('#bankDetails').attr('hidden', true);
                $('#bankAmountId').attr('hidden', true);
                $('.creditDetails').attr('hidden', true);
            })
        }

        saleItemGridTBody.on('keyup', '.qty', function () {
            if ($(this).val() < 0) {
                let selectedRow = $(this).closest('tr');
                selectedRow.find('.qty').val(selectedRow.find('.initialQty').val());
                errorMsg("Invalid quantity");
            }
            calculateTotal()
        });


        function calculateTotal() {
            let grandTotal = 0;
            let totalAmount = 0;
            saleItemGridTBody.find('tr').each(function () {
                var row = $(this).closest('tr');
                if (typeof row.find('.sellingPrice').val() != 'undefined' && typeof row.find('.qty').val() != 'undefined') {
                    totalAmount = (row.find('.sellingPrice').val() === '' ? 1 : parseFloat(row.find('.sellingPrice').val())) *
                        (row.find('.qty').val() === '' ? 1 : parseFloat(row.find('.qty').val()));
                }
                row.find('.totalAmount').val(totalAmount);
                grandTotal = grandTotal + totalAmount;
            });
            $('#grandTotal').val(grandTotal);
            $('#amtReturn').val((parseInt(Math.abs($('#amountReceivedInBank').val())) + parseInt(Math.abs($('#amtReceived').val())) + parseInt($('#discountRate').val())) - grandTotal)

            if (parseFloat(grandTotal) === 0) {
                $('#amtReceived').val(0);
                $('#discountRate').val(0);
                $('#isCash').val('');
                $('#invoiceNo').val('');
                $('#amtReturn').val(0);
                $('#bankDetails').attr('hidden', true);
                $('#bankAmountId').attr('hidden', true);
                $('.creditDetails').attr('hidden', true);
                resetPartRelated();
            }
        }

        function resetPartRelated() {
            $('#partyName').val('');
            $('#partyAddress').val('');
            $('#partyContactNo').val('');
            $('#partyEmail').val('');
        }


        function getSaleDetail() {
            let voucherNo = $('#voucherNo').val();
            if (voucherNo !== '' && voucherNo !== null) {
                $.ajax({
                    url: 'saleItem/getSaleDetail',
                    type: 'GET',
                    data: {voucherNo: voucherNo},
                    success: function (res) {

                        let iterator = 1;
                        if (res.saleItemListDTO.length > 0) {
                            populate(res);
                            saleItemGrid.dataTable().fnClearTable();
                            for (let i in res.saleItemListDTO) {

                                saleItemGrid.fnAddData(
                                    [
                                        "<td><input type='text' id='index' readonly class='form-control form-control-sm' value='" + iterator + "'></td>",
                                        "<td><input type='text' id='itemCode' readonly name='saleItemListDTO[" + i + "].itemCode' class='form-control form-control-sm' value='" + res.saleItemListDTO[i].itemCode + "'></td>",
                                        "<td><input type='text' readonly name='saleItemListDTO[" + i + "].itemName' class='form-control form-control-sm' value='" + res.saleItemListDTO[i].itemName + "'></td>",
                                        "<td><input type='text' readonly name='saleItemListDTO[" + i + "].unitName' class='form-control form-control-sm' value='" + res.saleItemListDTO[i].unitName + "'></td>",
                                        "<td><input type='text' readonly name='saleItemListDTO[" + i + "].sellingPrice' class='form-control form-control-sm sellingPrice right-align' value='" + res.saleItemListDTO[i].sellingPrice + "'></td>",
                                        "<td><input type='hidden' id='initialQty' readonly class='form-control form-control-sm initialQty right-align' value=" + res.saleItemListDTO[i].qty + " >" +
                                        "<input type='text' id='qty' readonly class='form-control form-control-sm qty right-align amount'   name='saleItemListDTO[" + i + "].qty' value=" + res.saleItemListDTO[i].qty + " ></td>",
                                        "<td><input type='text' readonly class='form-control form-control-sm totalAmount right-align totalAmount' id='totalAmount'  name='saleItemListDTO[" + i + "].totalAmount' value=" + res.saleItemListDTO[i].totalAmount + " ><input type='hidden' readonly class='form-control form-control-sm formatDate right-align' id='saleDate'  name='saleItemListDTO[" + i + "].saleDate' value=" + formatAsDate(res.saleItemListDTO[i].saleDate) + " ></td>",
                                        "<td><input type='button'  id='itemEditBtn' class='btn btn-sm btn-primary btn-xs fa fa-trash' value='Edit'></td>"
                                    ]
                                );
                                iterator = iterator + 1;
                            }
                            calculateTotal();

                            if (res.isCash === 2) {
                                $('#bankDetails').attr('hidden', false);
                            } else {
                                $('#bankDetails').attr('hidden', true);
                            }

                            if (res.partyName !== null && res.partyName !== '') {
                                $('.creditDetails').attr('hidden', false);
                                $.ajax({
                                    url: 'saleInvoiceGeneration/getPartyDetail',
                                    type: 'GET',
                                    data: {partyName: res.partyName},
                                    success: function (res) {
                                        populate(res);
                                    }
                                })
                            } else {
                                $('.creditDetails').attr('hidden', true);
                                resetPartRelated();
                            }

                            if (res.isCash === 4) {
                                $('#bankDetails').attr('hidden', false);
                                $('#amountReceivedText').text("Amount in Cash");
                                $('#bankAmountId').attr('hidden', false);
                            }

                            $('#printBtn').attr('disabled', false)
                        }
                    }
                });
            }
        }

        function onFocusOut() {
            $('#qty').on('focusout', function () {
                let itemCode = $('#itemCode').val();
                if (itemCode !== '' && typeof itemCode !== 'undefined') {
                    checkAvailabilityOfItem(itemCode, $('#qty').val(), '');
                }
            })
        }

        function checkAvailabilityOfItem(itemCode, qty, selectedRow) {
            if (itemCode !== '' && qty !== '') {
                $.ajax({
                    url: 'saleItem/getAvailableQty',
                    type: 'GET',
                    data: {itemCode: itemCode},
                    success: function (res) {

                        let qtyId = $('#qty');
                        let storeQty = parseFloat(res);

                        if (selectedRow !== '') {

                            let initialQty = selectedRow.find('.initialQty').val() === '' ? 0 : parseFloat(selectedRow.find('.initialQty').val());

                            if (initialQty !== 0) {
                                if (initialQty <= qty && storeQty === 0) {
                                    selectedRow.find('.qty').val(initialQty);
                                    errorMsg('Qty exceeded than available qty ' + (initialQty - qty).toFixed(1));
                                    return false;
                                }
                                if (initialQty <= qty && storeQty === 0 || (initialQty + storeQty) < qty) {
                                    errorMsg('Qty exceeded than available qty ' + ((initialQty + storeQty) - qty).toFixed(1));
                                    selectedRow.find('.qty').val(initialQty);
                                    return false;
                                }
                            } else {
                                if (storeQty < parseFloat(qty)) {
                                    selectedRow.find('.qty').val(initialQty);
                                    errorMsg('Qty exceeded than available qty ' + (parseFloat(qty) - storeQty)).toFixed(1);
                                }
                            }
                        } else {
                            if (storeQty <= 0) {
                                qtyId.val('');
                                $('#itemCode').focus();
                                errorMsg('Item out of stock.');
                            }
                            if (qtyId.val() > storeQty) {
                                qtyId.focus();
                                qtyId.val('');
                                errorMsg('Qty exceeded than available qty ' + (parseFloat(qty) - storeQty).toFixed(1));
                            } else {
                                addGridToDetails();
                            }
                        }
                    }
                })
            }
        }

        function calculateReturnAmt() {

            let calculatedAmount = 0,
                amtReceived = 0,
                discountRate = 0,
                amountReceivedInBank = 0,
                amtReturn = $('#amtReturn'),
                grandTotal = $('#grandTotal'),
                discountRateId = $('#discountRate'),
                amtReceivedId = $('#amtReceived'),
                amountReceivedInBankId = $('#amountReceivedInBank'),
                isCash = $('#isCash');

            amtReceivedId.on('keyup', function () {
                    amtReceived = $(this).val() === '' ? 0 : parseFloat($(this).val());
                    if (parseInt(isCash.val()) !== 4) {
                        calculatedAmount = amtReceived - (parseFloat(grandTotal.val()) - parseFloat(discountRateId.val()));
                        amtReturn.val(calculatedAmount);
                    } else {
                        if (parseFloat(grandTotal.val()) <= parseFloat(amtReceivedId.val())) {

                            if (parseFloat(grandTotal.val()) < parseFloat(amtReceivedId.val())) {
                                amountReceivedInBankId.val(0);
                            }
                            amountReceivedInBank = amountReceivedInBankId.val() === '' || amountReceivedInBankId.val() === 0 ? 0 : parseFloat(amountReceivedInBankId.val());
                            calculatedAmount = (amountReceivedInBank + amtReceived)

                            if (discountRateId.val() > 0) {
                                calculatedAmount = (parseFloat(discountRateId.val()) + calculatedAmount) - parseFloat(grandTotal.val());
                            } else {
                                calculatedAmount = calculatedAmount - parseFloat(grandTotal.val());
                            }

                            if (parseFloat(grandTotal.val()) > parseFloat(amtReceivedId.val())) {
                                amountReceivedInBankId.val(calculatedAmount);
                            }
                            amtReturn.val(calculatedAmount);
                        } else {
                            if (parseInt(isCash.val()) === 4) {
                                calculatedAmount = grandTotal.val() - amtReceivedId.val();
                                if (parseFloat(discountRateId.val()) > 0) {
                                    calculatedAmount = (calculatedAmount - parseFloat(discountRateId.val()));
                                }
                                if (parseFloat(calculatedAmount) > 0) {
                                    amountReceivedInBankId.val(calculatedAmount);
                                } else {
                                    amountReceivedInBankId.val(0);
                                }
                                amtReturn.val(grandTotal.val() - (parseFloat(amtReceivedId.val()) + parseFloat(amountReceivedInBankId.val()) + parseFloat(discountRateId.val())));
                            } else {
                                calculatedAmount = grandTotal.val() - amtReceivedId.val();
                                if (discountRateId.val() > 0) {
                                    calculatedAmount = (parseFloat(discountRateId.val()) + calculatedAmount);
                                }
                                amountReceivedInBankId.val(calculatedAmount);
                                amtReturn.val(grandTotal.val() - (parseFloat(amtReceivedId.val())));
                            }

                        }
                    }

                    if (parseInt(isCash.val()) !== 4) {
                        if (amtReturn.val() < 0) {
                            $('#amountReceivedText').text("Amount Received");
                            $('#bankDetails').attr('hidden', true);
                            $('#bankAmountId').attr('hidden', true);
                            $('.creditDetails').attr('hidden', false);

                        } else {
                            $('#amountReceivedText').text("Amount Received");
                            $('.creditDetails').attr('hidden', true);
                            resetPartRelated();
                        }
                        if (parseInt(isCash.val()) === 2) {
                            $('#bankDetails').attr('hidden', false);
                        } else {
                            $('#bankDetails').attr('hidden', true);
                        }
                    }
                }
            );

            amountReceivedInBankId.on('keyup', function () {
                amountReceivedInBank = $(this).val() === '' ? 0 : parseFloat($(this).val());
                if (parseInt(isCash.val()) === 4) {
                    calculatedAmount = grandTotal.val() - amtReceivedId.val();
                    if (parseFloat(discountRateId.val()) > 0) {
                        calculatedAmount = (calculatedAmount - parseFloat(discountRateId.val()));
                    }
                    amountReceivedInBankId.val(calculatedAmount);
                    amtReturn.val(grandTotal.val() - (parseFloat(amtReceivedId.val()) + parseFloat(amountReceivedInBankId.val()) + parseFloat(discountRateId.val())));
                } else {
                    calculatedAmount = (parseFloat(amtReceivedId.val()) + amountReceivedInBank);
                    if (discountRate > 0 && calculatedAmount > 0) {
                        calculatedAmount = (parseFloat(discountRateId.val()) + calculatedAmount) - parseFloat(grandTotal.val());
                    } else {
                        calculatedAmount = calculatedAmount - parseFloat(grandTotal.val());
                    }
                    amtReturn.val(calculatedAmount);
                }
            });

            discountRateId.on('keyup', function () {

                if (parseFloat($(this).val()) > parseFloat(grandTotal.val())) {
                    $(this).val('');
                    amtReceivedId.attr('readonly', false);
                    errorMsg('Discount figure more than sales');
                } else {

                    if (parseInt(isCash.val()) === 2) {
                        if (parseFloat($(this).val()) === parseFloat(grandTotal.val())) {
                            amtReceivedId.val(0);
                            amtReceivedId.attr('readonly', true);
                        } else {
                            amtReceivedId.attr('readonly', false);
                        }
                    }

                    if (parseInt(isCash.val()) === 4) {
                        calculatedAmount = grandTotal.val() - amtReceivedId.val();
                        if (parseFloat(discountRateId.val()) > 0) {
                            calculatedAmount = (calculatedAmount - parseFloat(discountRateId.val()));
                        }
                        amountReceivedInBankId.val(calculatedAmount);
                        amtReturn.val(grandTotal.val() - (parseFloat(amtReceivedId.val()) + parseFloat(amountReceivedInBankId.val()) + parseFloat(discountRateId.val())));
                    } else {
                        discountRate = $(this).val() === '' ? 0 : parseFloat($(this).val());
                        calculatedAmount = (parseFloat(amountReceivedInBankId.val()) + parseFloat(amtReceivedId.val()) + discountRate);
                        amtReturn.val(calculatedAmount - grandTotal.val());
                    }

                    if (parseInt(isCash.val()) !== 4) {

                        if (amtReturn.val() < 0) {
                            $('#amountReceivedText').text("Amount Received");
                            $('#bankDetails').attr('hidden', true);
                            $('#bankAmountId').attr('hidden', true);
                            $('.creditDetails').attr('hidden', false);

                        } else {
                            $('#amountReceivedText').text("Amount Received");
                            $('.creditDetails').attr('hidden', true);
                            resetPartRelated();
                        }
                        if (parseInt(isCash.val()) === 2) {
                            $('#bankDetails').attr('hidden', false);
                        } else {
                            $('#bankDetails').attr('hidden', true);
                        }
                    }

                }
            })
        }

        function returnItemOnClick() {
            $('#returnItem').on('click', function () {
                $('#replacementItemModal').modal('show');
            });
        }

        function onCreditPurchaseSelect() {
            $('#isCash').on('change', function () {

                if (parseInt($('#counterOrSupply').val()) === 2) {
                    if (parseInt($(this).val()) !== 3) {
                        errorMsg("It's supply sale.");
                        $('#isCash').val(3);
                        return;
                    }
                }

                let amountReceivedText = $('#amountReceivedText')
                amountReceivedText.text("Amount Received");

                $('#discountRate').val(0);
                $('#amtReceived').val(0);
                $('#amountReceivedInBank').val(0);
                calculateTotal();

                if (parseInt($(this).val()) === 3) {
                    $('.creditDetails').attr('hidden', false);
                } else {
                    $('.creditDetails').attr('hidden', true);
                    resetPartRelated();
                }

                if (parseInt($(this).val()) === 2 || parseInt($(this).val()) === 4) {
                    $('#bankDetails').attr('hidden', false);
                    if (parseInt($(this).val()) === 4) {
                        amountReceivedText.text("Amount in Cash");
                        $('#bankAmountId').attr('hidden', false);
                    } else {
                        $('#bankAmountId').attr('hidden', true);
                    }
                } else {
                    $('#bankDetails').attr('hidden', true);
                    $('#bankAmountId').attr('hidden', true);
                }
            })
        }

        let partyList;

        function getPartyList() {
            $.ajax({
                url: 'saleInvoiceGeneration/getPartyList',
                type: 'GET',
                success: function (res) {
                    partyList = $.map(res, function (value) {
                        return {value: value.partyName};
                    });

                    $("#partyName").devbridgeAutocomplete({
                        lookup: partyList,
                        onSelect: function (suggestion) {
                            $.ajax({
                                url: 'saleInvoiceGeneration/getPartyDetail',
                                type: 'GET',
                                data: {partyName: suggestion.value},
                                success: function (res) {
                                    populate(res);
                                }
                            })
                        }
                    });
                }
            });
        }

        function getItemCodeList() {
            $.ajax({
                url: 'saleItem/getItemCodeList',
                type: 'GET',
                success: function (res) {
                    $('#itemCode').devbridgeAutocomplete({
                        lookup: $.map(res, function (value) {
                            return {data: value.id, value: value.text}
                        }), onSelect: function (suggestion) {
                            fetchDetail(suggestion.data);
                            $('#itemCode').val(suggestion.data);
                        }
                    })
                }
            })
        }

        function onCounterOrSupply() {
            $('#counterOrSupply').on('change', function () {
                if (parseInt($(this).val()) === 1) {
                    $('#sellingPrice').val('');
                    fetchDetail($('#itemCode').val());
                    $('#sellingPrice').attr('readOnly', true);
                } else {
                    $('#sellingPrice').attr('readOnly', false);
                }
            })
        }

        $('#counterOrSupply').on('change', function () {
            if (parseInt($(this).val()) === 2) {
                $('#isCash').val(3);
                $('.creditDetails').attr('hidden', false);
            } else {
                $('#isCash').val('');
                $('.creditDetails').attr('hidden', true);
                resetPartRelated();
            }
        });


        return {
            generateReceipt: generateReceipt,
            getItemDetails: getItemDetails,
            onBtnClickDeleteItem: onBtnClickDeleteItem,
            onBtnClickEditItem: onBtnClickEditItem,
            getSaleDetail: getSaleDetail,
            calculateReturnAmt: calculateReturnAmt,
            onFocusOut: onFocusOut,
            returnItemOnClick: returnItemOnClick,
            onCreditPurchaseSelect: onCreditPurchaseSelect,
            getPartyList: getPartyList,
            getItemCodeList: getItemCodeList,
            validateQty: validateQty,
            onCounterOrSupply: onCounterOrSupply

        }
    }

)();
$(document).ready(function () {
    saleItem.generateReceipt();
    saleItem.getItemDetails();
    saleItem.onBtnClickDeleteItem();
    saleItem.onBtnClickEditItem();
    saleItem.getSaleDetail();
    saleItem.calculateReturnAmt();
    saleItem.onFocusOut();
    saleItem.returnItemOnClick();
    saleItem.onCreditPurchaseSelect();
    saleItem.getPartyList();
    saleItem.getItemCodeList();
    saleItem.validateQty();
    saleItem.onCounterOrSupply();

});
