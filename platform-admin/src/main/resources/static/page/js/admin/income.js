(function (opt) {
    opt.newIncomeOpts = function (data) {
        var option = opt.income;
        for (var i = 0; i < data.length; i++) {
            var serie = option.series[i];
            serie.data = data[i];
            option.series[i] = serie;
        }
        return option;
    }
    opt.income = {
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['用户增量','记录增量']
        },
        toolbox: {
            show: true,
            feature: {
                mark: { show: true },
                dataView: { show: true, readOnly: false },
                magicType: { show: true, type: ['line', 'bar', 'stack', 'tiled'] },
                restore: { show: true },
                saveAsImage: { show: true }
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                boundaryGap: false,
                data: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31]
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '用户增量',
                type: 'line',
                label: {
                    normal: {
                        show: true,
                        position: 'top'
                    }
                },
                itemStyle: {
                    normal: {
                        color: "#1E9FFF",
                        areaStyle: { type: 'default' }
                    }
                },
                areaStyle: {
                    normal: {
                        color: "#1E9FFF"
                    }
                },
                data: []
            },
            {
                name: '记录增量',
                type: 'line',
                label: {
                    normal: {
                        show: true,
                    }
                },
                itemStyle: {
                    normal: {
                        areaStyle: { type: 'default' }
                    }
                },
                
                data: []
            }
        ]
    };
})(window.opts || (window.opts = {}));

$(function () {
    var container = $("#flot-line-chart-moving");
    var maximum = container.outerWidth() / 2 || 300;
    var data = [];

    function getRandomData() {
        if (data.length) {
            data = data.slice(1);
        }
        while (data.length < maximum) {
            var previous = data.length ? data[data.length - 1] : 50;
            var y = previous + Math.random() * 10 - 5;
            data.push(y < 0 ? 0 : y > 100 ? 100 : y);
        }
        var res = [];
        for (var i = 0; i < data.length; ++i) {
            res.push([i, data[i]])
        }
        return res;
    }
    series = [{
        data: getRandomData(),
        lines: {
            fill: true
        }
    }];
    var plot = $.plot(container, series, {
        grid: {
            color: "#999999",
            tickColor: "#f7f9fb",
            borderWidth: 0,
            minBorderMargin: 20,
            labelMargin: 10,
            backgroundColor: {
                colors: ["#ffffff", "#ffffff"]
            },
            margin: {
                top: 8,
                bottom: 20,
                left: 20
            },
            markings: function (axes) {
                var markings = [];
                var xaxis = axes.xaxis;
                for (var x = Math.floor(xaxis.min); x < xaxis.max; x += xaxis.tickSize * 2) {
                    markings.push({
                        xaxis: {
                            from: x,
                            to: x + xaxis.tickSize
                        },
                        color: "#fff"
                    });
                }
                return markings;
            }
        },
        colors: ["#4fc5ea"],
        xaxis: {
            tickFormatter: function () {
                return "";
            }
        },
        yaxis: {
            min: 0,
            max: 110
        },
        legend: {
            show: true
        }
    });

    // Update the random dataset at 25FPS for a smoothly-animating chart

    setInterval(function updateRandom() {
        series[0].data = getRandomData();
        plot.setData(series);
        plot.draw();
    }, 40);
});