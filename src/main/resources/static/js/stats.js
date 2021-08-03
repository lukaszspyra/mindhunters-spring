var xLabels1 = [];
var xLabels2 = [];
var xLabels3 = [];

var xData1 = [];
var xData2 = [];
var xData3 = [];


function drawBarCharts(labels, data, chartNo, type) {

    var ctx = document.getElementById(chartNo);
    var myChart = new Chart(ctx, {
        type: type,
        data: {
            labels: labels,
            datasets: [{
                label: '',
                data: data,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)',
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
                    'rgba(255, 159, 64, 1)',
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
            legend: {
                display: false,
            },
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });
}


function drawPieCharts(labels, data, chartNo, type) {

    var ctx = document.getElementById(chartNo);
    var myChart = new Chart(ctx, {
        type: type,
        data: {
            labels: labels,
            datasets: [{
                label: '',
                data: data,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)',
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
                    'rgba(255, 159, 64, 1)',
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)'
                ],
                borderWidth: 1
            }]
        },
    });
}

function getData() {
    $.getJSON('/api/stats/drinks/category', function (data1) {

        for (let i = 0; i < data1.length; i++) {
            let pairDrinkQuantity1 = data1[i];
            const name1 = pairDrinkQuantity1.name;
            const quantity1 = pairDrinkQuantity1.quantity;
            xLabels1.push(name1);
            xData1.push(quantity1);

        }
        drawBarCharts(xLabels1, xData1, 'myChart1', 'bar');
    });

    $.getJSON('/api/stats/categories', function (data2) {

        for (let i = 0; i < data2.length; i++) {
            let pairDrinkQuantity2 = data2[i];
            const name2 = pairDrinkQuantity2.name;
            const quantity2 = pairDrinkQuantity2.quantity;
            xLabels2.push(name2);
            xData2.push(quantity2);

        }
        drawPieCharts(xLabels2, xData2, 'myChart2', 'pie');
    });


    $.getJSON('/api/stats/drinks/top-10', function (data3) {

        for (let i = 0; i < data3.length; i++) {
            let pairDrinkQuantity3 = data3[i];
            const name3 = pairDrinkQuantity3.name;
            const quantity3 = pairDrinkQuantity3.quantity;
            xLabels3.push(name3);
            xData3.push(quantity3);

        }
        drawBarCharts(xLabels3, xData3, 'myChart3', 'bar');
    });
}

$(function () {
    $(document).ready(function () {
        getData();
    });
});








