// let fileArray = [];
//
// $('#addFileToListBtn').on("click", () => {
//
//     const files = $("#file")[0].files;
//
//     // Check if files are selected
//     if (files.length === 0) {
//         errorMsg("No files selected");
//         return; // Exit the function if no files are selected
//     }
//
//     // Add files to the file array (excluding duplicates)
//     for (let i = 0; i < files.length; i++) {
//         const file = files[i];
//         if (!isFileDuplicate(file)) {
//             fileArray.push({file: file, fileNameToReplace: null});
//             successMsg('Successfully Added!')
//         } else {
//             errorMsg("File already exists!");
//         }
//     }
//
//     // Remove the "No file added" row if it exists
//     $('#noFileRow').remove();
//
//     // Check if files are added
//     if (fileArray.length === 0) {
//         // No files added, append the "No file added" row
//         const noFileRow = `<tr id="noFileRow"><td colspan="3">No file added</td></tr>`;
//         $('#FileList').html(noFileRow);
//     } else {
//         // Files are added, update the table rows
//         let htmlContent = '';
//         for (let i = 0; i < fileArray.length; i++) {
//             htmlContent += `
//             <tr id="fileRow${i}">
//                 <th>${i + 1}</th>
//                 <th>
//                     <input class="fileName form-control" id="FileNameAt${i}" name="fileName[${i}]" value="${fileArray[i].file.name}"
//                     onchange="onFileNameChanged(${fileArray.indexOf(fileArray[i])},${i})">
//                 </th>
//                 <th>
//                     <input type="button" class="btn btn-sm btn-danger text-center " value="Delete" onclick="onDelete(${i})">
//                 </th>
//             </tr>`;
//         }
//         $('#FileList').html(htmlContent);
//
//     }
//
//     // Files are added, perform the desired action
//     // Example: Enable a button or trigger another event
//
//
//     isFileArrayEmpty()
// });
// function onFileNameChanged(position,index) {
//
//     console.log(position)
//     console.log(index)
//     fileArray[position].fileNameToReplace = $('#FileNameAt' + index).val();
//
//     console.log(fileArray);
// }
//
// function onDelete(index) {
//
//     confirmMessage("Are you sure you want to delete?", (e) => {
//
//         if (e) {
//             const file = fileArray[index];
//             fileArray.splice(index, 1);
//             // Remove the corresponding HTML element
//             $(`#fileRow${index}`).remove();
//
//             // Update the count variable
//             let count = $('#FileList tr').length;
//
//             // Update the index of the remaining file elements
//             $('#FileList tr').each(function (i) {
//                 $(this).find('th:first').text(i + 1);
//                 $(this).find('input[name^="fileName"]').attr('name', `fileName[${i}]`);
//                 $(this).find('input[type="button"]').attr('onclick', `onDelete(${i})`);
//             });
//
//             // Check if files are added
//             if (fileArray.length === 0) {
//                 // No files added, append the "No file added" row
//                 const noFileRow = `<tr id="noFileRow"><td colspan="3">No file added</td></tr>`;
//                 $('#FileList').html(noFileRow);
//             }
//             isFileArrayEmpty()
//             // console.log("Removing file " + file + " at " + index);
//             swal.close();
//         }
//     });
//
//
// }
//


let fileArray = [];

$('#addFileToListBtn').on("click", () => {
    const files = $("#file")[0].files;

    // Check if files are selected
    if (files.length === 0) {
        errorMsg("No files selected");
        return; // Exit the function if no files are selected
    }

    // Add files to the file array (excluding duplicates)
    for (let i = 0; i < files.length; i++) {
        const file = files[i];
        if (!isFileDuplicate(file)) {
            fileArray.push({file: file, fileNameToReplace: null});

            successMsg('Successfully Added!');
        } else {
            errorMsg("File already exists!");
        }
    }

    // Remove the "No file added" row if it exists
    $('#noFileRow').remove();

    // Check if files are added
    if (fileArray.length === 0) {
        // No files added, append the "No file added" row
        const noFileRow = `<tr id="noFileRow"><td colspan="3">No file added</td></tr>`;
        $('#FileList').html(noFileRow);
    } else {
        // Files are added, update the table rows
        let htmlContent = '';
        for (let i = 0; i < fileArray.length; i++) {
            htmlContent += `
            <tr id="fileRow${i}">
                <th>${i + 1}</th>
                <th>
                    <input class="fileName form-control" id="FileNameAt${i}" name="fileName[${i}]" value="${fileArray[i].file.name}"
                    onchange="onFileNameChanged(this, ${i})">
                </th>
                <th>
                    <input type="button" class="btn btn-sm btn-danger text-center " value="Delete" onclick="onDelete(${i})">
                </th>
            </tr>`;
        }
        $('#FileList').html(htmlContent);
    }

    $('#file').val('');
    $('#preview').empty();
    $('#preview').text('Upload Image')

    isFileArrayEmpty();

});

function onFileNameChanged(input, index) {
    const fileNameToReplace = $(input).val();
    fileArray[index].fileNameToReplace = fileNameToReplace;
    console.log(fileArray);
}



function onDelete(index) {
    confirmMessage("Are you sure you want to delete?", (e) => {
        if (e) {
            fileArray.splice(index, 1);
            $(`#fileRow${index}`).remove();

            // Update the index of the remaining file elements
            $('#FileList tr').each(function (i) {
                const row = $(this);
                const currentIndex = parseInt(row.attr('id').replace('fileRow', ''));
                if (currentIndex > index) {
                    const newIndex = currentIndex - 1;
                    row.attr('id', `fileRow${newIndex}`);
                    row.find('th:first').text(newIndex + 1);
                    row.find('.fileName').attr('id', `FileNameAt${newIndex}`).attr('name', `fileName[${newIndex}]`).attr('onchange', `onFileNameChanged(this, ${newIndex})`);
                    row.find('.btn-danger').attr('onclick', `onDelete(${newIndex})`);
                }
            });

            if (fileArray.length === 0) {
                const noFileRow = `<tr id="noFileRow"><td colspan="3">No file added</td></tr>`;
                $('#FileList').html(noFileRow);
            }

            isFileArrayEmpty();
            swal.close();
        }
    });
}


function isFileDuplicate(file) {
    return fileArray.some(existingFile => existingFile.name === file.name && existingFile.size === file.size);
}


$('#file').on("change", () => {
    isFileArrayEmpty()
    const fileInput = $("#file")[0];
    const file = fileInput.files[0];

    $('#fileSize').text("File Size : " + getFileSizeInMB(file) + "MB");
    if (file && getFileSizeInMB(file) > 10) {
        errorMsg('File size should not exceed 10')
        $('#addFileToListBtn').attr('disabled', true)
    } else {
        $('#addFileToListBtn').attr('disabled', false)
    }


    if (file && file.type.startsWith("image/")) {
        const reader = new FileReader();
        reader.onload = function (e) {
            const imageSrc = e.target.result;
            $('#preview').html(`<img src="${imageSrc}" alt="${file.name}"/> `);
        }
        reader.readAsDataURL(file);
    } else {
        $('#preview').html('');
    }

});

function getFileSizeInMB(file) {
    const fileSizeInBytes = file.size;
    const fileSizeInMB = fileSizeInBytes / (1024 * 1024);
    return fileSizeInMB.toFixed(2);
}


$('#selectNew').on("click", () => {

    $('#file').click();
});


function isFileArrayEmpty() {
    if (fileArray.length === 0) {
        $("#uploadBtn").attr('disabled', true);
        $('#cancelBtn').attr('disabled', true);
    } else {
        $("#uploadBtn").attr('disabled', false);
        $('#cancelBtn').attr('disabled', false);
    }
}

isFileArrayEmpty();

$('#cancelBtn').on('click', () => {
    confirmMessage("Are you sure you want to cancel?", (e) => {
        if (e) {

            $('#FileList').empty();
            // Empty the fileArray
            fileArray = [];
            isFileArrayEmpty();
            const noFileRow = `<tr id="noFileRow"><td colspan="3">No file added</td></tr>`;
            $('#FileList').html(noFileRow);
            successMsg("Cancelled Successfully")
        }

    })
});


$('#uploadBtn').on('click', () => {

    const formData = new FormData();
    // Append each file to the FormData object
    for (let i = 0; i < fileArray.length; i++) {
        formData.append('file', fileArray[i].file);
        formData.append('fileNameToReplace', fileArray[i].fileNameToReplace);
    }
    $.ajax({
        url: "fileUpload/uploadFiles",
        type: "POST",
        data: formData,
        // data: JSON.stringify(fileArray),
        processData: false,
        contentType: false,

        success: function (res) {
            // alert("Testing")
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


