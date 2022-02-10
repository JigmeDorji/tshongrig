index = (function () {


    function getSelectedCompanyDetails() {
        let splitURL = location.search.split('companyId=')[1];
        $.ajax({
            url: 'index/getSelectedCompanyDetails',
            type: 'GET',
            data: {companyId: splitURL.split('&&')[0]},
            success: function (res) {
                window.location.href = spms.getUrl() + 'index';
            }
        });

    }

    function getTotalSale() {
        $.ajax({
            url: 'index/getTotalSale',
            type: 'GET',
            success: function (res) {
                let ctx = document.getElementById('myChart');
                let myChart = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: res.saleListDate,
                        datasets: [{
                            label: 'Day wise total Sale',
                            data: res.totalListSale,
                            backgroundColor: [
                                'rgba(255, 99, 132, 0.2)',
                                'rgba(54, 162, 235, 0.2)',
                                'rgba(255, 206, 86, 0.2)',
                                'rgba(75, 192, 192, 0.2)',
                                'rgba(153, 102, 255, 0.2)',
                                'rgba(255, 159, 64, 0.2)'
                            ],
                            borderColor: [
                                'rgba(255, 99, 132, 1)',
                                'rgba(54, 162, 235, 1)',
                                'rgba(255, 206, 86, 1)',
                                'rgba(75, 192, 192, 1)',
                                'rgba(153, 102, 255, 1)',
                                'rgba(255, 159, 64, 1)'
                            ],
                            borderWidth: 1
                        }]
                    },
                    options: {
                        scales: {
                            y: {
                                beginAtZero: true
                            }
                        }
                    }
                });
            }
        });
    }

    return {
        getTotalSale: getTotalSale,
        getSelectedCompanyDetails: getSelectedCompanyDetails
    }
})();
$(document).ready(function () {
    index.getTotalSale();
    index.getSelectedCompanyDetails();
});
