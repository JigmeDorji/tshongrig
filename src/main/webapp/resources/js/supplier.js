/**
 * Created by jigmePc on 10/20/2019.
 */
supplier = (function () {
    //To save the data
    function save() {
        $('.globalForm').validate({
            submitHandler: function (form) {
                $.ajax({
                    url: 'supplierSetup/save',
                    type: 'POST',
                    data: $(form).serializeArray(),
                    success: function (res) {
                        if (res.status == 1) {
                            $(form)[0].reset();
                            successMsg(res.text);
                            $('#stationId').attr('readonly', false);
                            getSupplierList();
                        }
                    }
                });
            }
        });
    };

    function getSupplierList() {
        $('#supplierListTable').dataTable().fnDestroy();
        $.ajax({
            url: 'supplierSetup/getSupplierList',
            type: 'GET',
            success: function (res) {
                var columnDef = [
                    {data: 'id', class: 'hidden'},
                    {data: 'supplierName'},
                    {data: 'address'},
                    {data: 'email'},
                    {data: 'contactNo'},
                    {
                        data: 'action',
                        render: function (data) {
                            let editBtn = '';
                            let hasEditRole = $('#hasEditRole').val();
                            if (hasEditRole.toString() === 'true') {
                                editBtn = '<button type="button" class="btn btn-primary btn-xs" id="editBtn"><i class="fa fa-edit"></i> Edit</button>';
                            }
                            return editBtn;
                        }
                    },
                ];
                $('#supplierListTable').DataTable({
                    data: res,
                    columns: columnDef
                });

            }

        });
    }

    function onClickBtnEdit() {
        $('#supplierListTable').find('tbody').on('click', 'tr #editBtn', function () {
            $('#supplierListTable tbody tr').removeClass('selected');
            var selectedRow = $(this).closest('tr').addClass('selected');
            var id = selectedRow.find("td:eq(0)").text();
            $.ajax({
                url: 'supplierSetup/getSupplierDetails',
                type: 'GET',
                data: {id: id},
                success: function (res) {
                    populate(res);
                }
            });
        });
    }



    return {
        save: save,
        getSupplierList: getSupplierList,
        onClickBtnEdit: onClickBtnEdit
    }
})();

$(document).ready(function () {
    supplier.save();
    supplier.getSupplierList();
    supplier.onClickBtnEdit();
});
