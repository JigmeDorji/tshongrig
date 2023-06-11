function loadingOperationOnDOMStatus() {

    let loaderTimeLimit = 10000;
    window.addEventListener('load', function () {
        // Calculate the window loading time
        loaderTimeLimit = performance.now();
    })
    setTimeout(() => {
        // Content loaded, close the loading dialog
        // alert("df")
        Swal.close();
        // Proceed with further actions
    // }, loaderxTimeLimit);
    }, 3000);

    Swal.fire({
        html: true,
        title: '<div>' +
            '  <div class="wrapper">' +
            '    <div class="circle"></div>' +
            '    <div class="circle"></div>' +
            '    <div class="circle"></div>' +
            '    <div class="shadow"></div>' +
            '    <div class="shadow"></div>' +
            '    <div class="shadow"></div>' +
            '  </div>' +
            '</div>',
        html: '<div></div>',
        position: 'top',
        width: '270px',
        showConfirmButton: false,
        allowOutsideClick: false,

    });


}

// loadingOperationOnDOMStatus();


//
// <style>
//     .lds-ellipsis {
//     display: inline-block;
//     position: relative;
//     width: 80px;
//     height: 80px;
// }
//
//
//     .lds-ellipsis div {
//     position: absolute;
//     top: 33px;
//     width: 13px;
//     height: 13px;
//     border-radius: 50%;
//     background: #fff;
//     animation-timing-function: cubic-bezier(0, 1, 1, 0);
// }
//
//     .lds-ellipsis div:nth-child(1) {
//     left: 8px;
//     animation: lds-ellipsis1 0.6s infinite;
// }
//
//     .lds-ellipsis div:nth-child(2) {
//     left: 8px;
//     animation: lds-ellipsis2 0.6s infinite;
// }
//
//     .lds-ellipsis div:nth-child(3) {
//     left: 32px;
//     animation: lds-ellipsis2 0.6s infinite;
// }
//


//     .lds-ellipsis div:nth-child(4) {
//     left: 56px;
//     animation: lds-ellipsis3 0.6s infinite;
// }
//
//     @keyframes lds-ellipsis1 {
//     0% {
//         transform: scale(0);
//     }
//     100% {
//     transform: scale(1);
// }
// }
//
//     @keyframes lds-ellipsis3 {
//     0% {
//         transform: scale(1);
//     }
//     100% {
//     transform: scale(0);
// }
// }
//
//     @keyframes lds-ellipsis2 {
//     0% {
//         transform: translate(0, 0);
//     }
//     100% {
//     transform: translate(24px, 0);
// }
// }
//
// </style>