locationSetUp = (function () {
    function init() {
        save();
        getLocationList();
        displayGridValueToField();
    }

    /* var locationTable = $('#locationTable').dataTable({
     "paging": true,
     "searching": false

     });*/

    //To save the data
    function save() {
        $('.globalForm').validate({
            submitHandler: function (form) {
                $('#btnSave').prop('disabled', true)
                $.ajax({
                    url: 'locationSetUp/save',
                    type: 'POST',
                    data: $(form).serializeArray(),
                    success: function (res) {
                        if (res.status == 1) {
                            swal({
                                    title: res.text,
                                    text: "Click OK to exit",
                                    type: "success"
                                }, function () {
                                    window.location.reload();
                                }
                            )
                        }
                    },
                    complete: () => {
                        $('#btnSave').prop('disabled', false)
                    }, error: () => {
                        $('#btnSave').prop('disabled', false)
                    }

                });
            }
        });
    }


    //to load the existing list of users
    function getLocationList() {
        $('#locationTable').dataTable().fnDestroy();
        $.ajax({
            url: 'locationSetUp/getLocationList',
            type: 'GET',
            success: function (res) {
                var col = [
                    {
                        "mRender": function (data, type, row, meta) {
                            return meta.row + 1;
                        }
                    },
                    {data: 'locationSetUpId', class: 'locationSetUpId hidden',},
                    {data: 'locationId', class: 'locationId'},
                    {data: 'description', class: 'description'}
                    , {
                        "data": 'null',
                        render: function (status) {
                            let editBtn = '';
                            let hasEditRole = $('#hasEditRole').val();
                            if (hasEditRole.toString() === 'true') {
                                editBtn = '<button type="button" id="btnEdit" class="btn btn-sm btn-primary btn-xs"><i class="fa fa-edit"></i> Edit<button';
                            }
                            return editBtn;
                        }
                    }
                ];
                $('#locationTable').DataTable({
                    data: res,
                    columns: col,
                    bSort: false,
                    columnDefs: [{
                        "defaultContent": "-",
                        "targets": "_all"
                    }]
                });

                /*for (var i in res) {
                 locationTable.fnAddData(
                 [
                 '<input type="hidden" id="locationSetUpId" class="form-control" readonly="true" value="' + res[i].locationSetUpId+ '"><input type="text" id="locationId" class="form-control" readonly="true" value="' + res[i].locationId+ '">',
                 '<input type="text" id="description" class="form-control" readonly="true" value="' + res[i].description + '">',
                 ]);
                 }*/
            }

        });
    }

    //to display list values to field
    function displayGridValueToField() {
        $('#locationTable tbody').on('click', 'tr #btnEdit', function () {
            $('#locationTable tbody tr').removeClass('selected');
            let selectedRow = $(this).closest('tr');
            selectedRow.addClass('selected');
            $('#locationSetUpId').val(selectedRow.find('.locationSetUpId').text());
            $('#locationId').val(selectedRow.find('.locationId').text());
            $('#description').val(selectedRow.find('.description').text());

        });
    }

    return {
        init: init,
        save: save,
        getLocationList: getLocationList,
        displayGridValueToField: displayGridValueToField
    }
})();

$(document).ready(function () {
    locationSetUp.init();
});