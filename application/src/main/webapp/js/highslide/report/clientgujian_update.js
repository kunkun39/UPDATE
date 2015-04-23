var sta_container1 = {
    chart: {
        type: 'line',
        renderTo: 'container1'
    },
    title: {
        text: '用户固件更新统计'
    },
    subtitle: {
        text: ''
    },
    xAxis: {
        categories: []
    },
    yAxis: {
        title: {
            text: '次数'
        },
        min:0
    },
    tooltip: {
        formatter: function() {
            return '<b>' + this.series.name + '</b><br/>' + this.x + '号: ' + this.y + '次';
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
            name: '固件升级',
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
        text: '用户更新统计'
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
                format: '{point.name}<br/>占总升级次数比{point.percentage:.1f} %'
            }
        }
    },
    legend: {
        enabled: true
    },
    credits:{
        text:'用户升级成功/失败比例统计'
    },
    series: [{
        type: 'pie',
        name: '升级比例',
        data: []
    }]
};
