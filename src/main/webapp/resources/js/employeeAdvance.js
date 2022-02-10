/**
 * Created by jigme.dorji on 10/27/2020.
 */


employeeAdvance = (function () {

    let baseURL = 'employeeAdvance/';


    function save() {
        $('.globalForm').validate({
            submitHandler: function (form) {
                let data = $(form).serializeArray();
                let empName=$("#empId option:selected" ).text();
                data.push({name: 'empName', value: empName});
                $.ajax({
                    url: baseURL + 'save',
                    type: 'POST',
                    data: data,
                    success: function (res) {
                        let empAdvanceDto=JSON.stringify(res.dto);
                        if (res.status === 1) {
                            swal({
                                // timer: 800,
                                type: "success",
                                title: res.text,
                                showConfirmButton: true
                            }, function () {
                                window.location.reload();
                                $.ajax({
                                    url: baseURL + 'generatePaymentVoucher',
                                    type: 'POST',
                                    data: {
                                        id:res.dto.id,
                                        voucherNo:res.dto.voucherNo,
                                        amount:res.dto.amount
                                    },
                                    async: false,
                                    success: function (res) {
                                        if (res.status === 1) {
                                            window.open(spms.baseReportLocation() + res.dto.reportName, '_blank');
                                        }
                                    }
                                });
                            });
                        } else {
                            swal({
                                type: "warning",
                                title: res.text,
                            });
                        }
                    }
                })
            }
        })
    }

    $('#isCash').on('change',function(){
        if($(this).val()==2){
            $('#bankDetails').attr('hidden',false);
        }else{
            $('#bankDetails').attr('hidden',true);
        }
    });

    return {
        save: save
    }
})();

$(document).ready(function () {
    employeeAdvance.save();

});

