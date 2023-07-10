/**
 * Created by jigmePc on 5/4/2019.
 */


viewNewFiles = (function () {

    function onViewNewFile() {
        $(".onViewBtn").on('click', function () {
            const dataId = parseInt($(this).data('id'));

            $.ajax({
                url: 'viewNewFiles/getFileById',
                type: 'POST',
                data: {fileId: dataId},
                success: function (res) {


                    if (res.id === dataId) {

                        $('#fileName').text(res.fileName)

                        let filePath = res.contextPath + "/resources/fileCenter/" + res.folderName + "/" + res.fileName;

                        if (res.isPdfFile === 1) {
                            $('#iFrameContainer').attr('src', filePath);
                            $('#pdfViewFrame').attr('hidden', false)

                            $('#previewImage').attr('src', '');
                            $('#previewImage').attr('hidden', true)

                        } else {

                            $('#iFrameContainer').attr('src', '');
                            $('#pdfViewFrame').attr('hidden', true)

                            $('#previewImage').attr('src', filePath);
                            $('#previewImage').attr('hidden', false)


                            $("#onViewModal .modal-body").css({
                                "display": "flex",
                                "justify-content": "center",
                                "align-items": "center",
                                "overflow": "auto"
                            });
                            $("#previewImage").css({
                                "max-width": "100%",
                                "max-height": "100%",
                                "margin": "0 auto"
                            });


                        }


                        $('#onMarkedAsCompletedBtn').attr('accessKey', dataId)
                        $('#onPrintBtn').attr('accessKey', res.isPdfFile)
                        $('#onDeleteBtn').attr('accessKey', res.id)


                        $('#onViewModal').modal('show');

                    } else {
                        errorMsg("Error occurred while Fetching file Details!")
                    }
                }
            });

        });
    }


    function onMarkedAsCompletedBtn() {

        $('#onMarkedAsCompletedBtn').on('click', function () {
            const dataId = parseInt($(this).attr('accessKey'));

            $.ajax({
                url: 'viewNewFiles/onMarkedAsCompletedBtn',
                type: 'POST',
                data: {fileId: dataId},
                success: function (res) {
                    if (res.status === 1) {
                        swal({
                                title: res.text,
                                text: "Click OK to exit",
                                type: "success"
                            }, function () {
                                window.location.reload();
                            }
                        )
                    } else if (res.status === 0) {
                        swal({
                            icon: 'error',
                            title: res.text,
                            text: 'Click OK to exit'
                        })
                    }
                }
            });

        });

    }

    function onPrintBtn() {
        $('#onPrintBtn').on('click', function () {
            const isPdf = parseInt($(this).attr('accessKey'));
            if (isPdf === 1) {
                const iframe = document.getElementById('iFrameContainer');
                iframe.contentWindow.print();
            } else {
                const imageSrc = $('#previewImage').attr('src');
                let imageWindow = window.open("", "_blank");
                imageWindow.document.write('<html><head><title>Print Image</title><style>@media print { body { width: 210mm; height: 297mm; } img#printImage { max-width: 100%; page-break-before: always; }</style></head><body><img id="printImage" src="' + imageSrc + '"></body></html>');
                imageWindow.document.close();

                imageWindow.onload = function () {
                    imageWindow.print();
                    imageWindow.close();
                };

            }

        });
    }


    function onDeleteBtn() {
        $('.onDeleteBtn').on('click', function () {

            confirmMessage("Are you sure you want to delete?", (e) => {
                if (e) {
                    const fileId = parseInt($(this).attr('accessKey'));
                    $.ajax({
                        url: 'viewNewFiles/onDeleteBtn',
                        type: 'POST',
                        data: {fileId: fileId},
                        success: function (res) {
                            if (res.status === 1) {
                                swal({
                                    title: res.text,
                                    text: "Click OK to exit",
                                    type: "success"
                                }, function () {
                                    window.location.reload();
                                });
                            } else if (res.status === 0) {
                                swal({
                                    icon: 'error',
                                    title: res.text,
                                    text: 'Click OK to exit'
                                });
                            }
                        }
                    });
                }
            })

        });
    }


    return {
        onViewNewFile: onViewNewFile,
        onMarkedAsCompletedBtn: onMarkedAsCompletedBtn,
        onPrintBtn: onPrintBtn,
        onDeleteBtn: onDeleteBtn

    }
})();
$(document).ready(function () {
    viewNewFiles.onViewNewFile();
    viewNewFiles.onMarkedAsCompletedBtn();
    viewNewFiles.onPrintBtn();
    viewNewFiles.onDeleteBtn();


});



