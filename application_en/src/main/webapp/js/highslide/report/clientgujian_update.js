var sta_container1 = {
    chart: {
        type: 'line',
        renderTo: 'container1'
    },
    title: {
        text: 'Box Update Statistic'
    },
    subtitle: {
        text: ''
    },
    xAxis: {
        categories: []
    },
    yAxis: {
        title: {
            text: 'Times'
        },
        min:0
    },
    tooltip: {
        formatter: function() {
            return '<b>' + this.series.name + '</b><br/> ' + this.x + ': ' + this.y + ' time';
        }
    },
    legend: {
        enabled: true
    },
    plotOptions: {
        line: {
            dataLabels: {
                enabled: true
            },
            enableMouseTracking: true
        }
    }, series: [
        {
            name: 'Update Statistic',
            data: []
        }
    ],
    credits:{
        text:''
    }
};

var sta_container2 = {
    chart: {
        renderTo: 'container2',
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false
    },
    title: {
        text: 'Box Update Statistic'
    },
    tooltip: {
        pointFormat: '<b>{series.name}: {point.percentage:.1f}%</b>'
    },
    plotOptions: {
        pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
                enabled: true,
                color: '#ffffff',
                connectorColor: '#bbbbbb',
                format: '{point.name}<br/> take up {point.percentage:.1f} %'
            }
        }
    },
    legend: {
        enabled: true
    },
    credits:{
        text:'Box Success/Failed Update Statistic'
    },
    series: [{
        type: 'pie',
        name: 'Update Times',
        data: []
    }]
};

var sta_container3 = {
    chart: {
        type: 'column',
        renderTo: 'container3'
    },
    title: {
        text: 'Box Update Statistic'
    },
    subtitle: {
        text: ''
    },
    xAxis: {
        categories: []
    },
    yAxis: {
        title: {
            text: 'Times'
        },
        min:0
    },
    tooltip: {
        formatter: function() {
            return '<b>' + this.series.name + this.y + '';
        }
    },
    legend: {
        enabled: true
    },
    plotOptions: {
        series: {
            colorByPoint: true
        },
        column: {
            dataLabels: {
                enabled: true
            },
            pointPadding:0.2,
            borderWidth:0,
            enableMouseTracking: true
        }
    }, series: [
        {
            name: 'totally update ',
            data: []
        }
    ],
    credits:{
        text:'Box Success/Failed Update Statistic'
    }
};

var sta_container4 = {
    chart: {
        renderTo: 'container4',
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false
    },
    title: {
        text: 'Box Update Statistic'
    },
    tooltip: {
        pointFormat: '<b>{series.name}: {point.percentage:.1f} %</b>'
    },
    plotOptions: {
        pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
                enabled: true,
                color: '#ffffff',
                connectorColor: '#bbbbbb',
                format: 'version {point.name}<br/>taken totally update {point.percentage:.1f} %'
            }
        }
    },
    legend: {
        enabled: true
    },
    credits:{
        text:'Box Success/Failed Update Statistic'
    },
    series: [{
        type: 'pie',
        name: 'Percent of update',
        data: []
    }]
};
