var source, sinPlot, cosPlot, sinOn = true, cosOn = true, plottingOn = true;
var xaxisValues = [];
var yaxis1Values = [];
var yaxis2Values = [];

if (typeof (EventSource) !== "undefined") {
    source = new EventSource("https://iolab.sk/evaluation/sse/sse.php");

    source.addEventListener("message", function (e) {
        var data = JSON.parse(e.data);
        document.getElementById("result").innerHTML = e.data;
        if(plottingOn){
            graphing(data);
        }
    }, false);

} else {
    document.getElementById("result").innerHTML = "Sorry, your browser does not support server-sent events...";
}

function plotGraphing() {
    sinPlot = {
        x: xaxisValues,
        y: yaxis1Values,
        name: 'Sínus',
        type: 'scatter',
        mode: 'lines',
        line: { color: '#ff0000', width: 4 }
    };
    cosPlot = {
        x: xaxisValues,
        y: yaxis2Values,
        name: 'Kosínus',
        type: 'scatter',
        mode: 'lines',
        line: { color: '#2a67f7', width: 4 }
    };
    var layout = {
        title: {
            text: 'Sínus a Kosínus',
            font: {
                family: 'Constantia',
                size: 45,
                color: "#ffffff"
            },
        },
        xaxis: {
            title: {
                text: 'Os X',
                font: {
                    family: 'Constantia',
                    size: 30,
                    color: "#ffffff"
                }
            },
            showgrid: false,
            zeroline: true,
            zerolinecolor: '#ffffff',
            zerolinewidth: 1,
            color: "#ffffff",
            tickfont: {
                size: 18
            }
        },
        yaxis: {
            title: {
                text: 'Os Y',
                font: {
                    family: 'Constantia',
                    size: 30,
                    color: "#ffffff"
                }
            },
            showgrid: true,
            zeroline: true,
            gridcolor: '#73767a',
            gridwidth: 1,
            zerolinecolor: '#ffffff',
            zerolinewidth: 1,
            color: "#ffffff",
            tickfont: {
                size: 18
            }
        },
        legend: {
            font: {
                color: "#ffffff",
                family: 'Constantia',
                size: 15,
            }
        },
        paper_bgcolor: "#000000",
        plot_bgcolor: "#000000"
    };
    var config = {responsive: true, scrollZoom: true};
    Plotly.newPlot('graph', [], layout,config);
    if(sinOn && cosOn) {
        Plotly.addTraces('graph', sinPlot);
        Plotly.addTraces('graph', cosPlot);  
    } else if(sinOn && !cosOn) {
        Plotly.addTraces('graph', sinPlot);
    } else if(!sinOn && cosOn) {
        Plotly.addTraces('graph', cosPlot);
    }
}

function graphing(data) {
    xaxisValues.push(data.x);
	yaxis1Values.push(data.y1*document.getElementById("rangeSlider").value);
	yaxis2Values.push(data.y2*document.getElementById("rangeSlider").value);
    plotGraphing();
}

var sinFunc = document.getElementById("sin");
sinFunc.addEventListener('change', function() {
    if (this.checked) {
        sinOn = true;
    } else {
        sinOn = false;
    }
    plotGraphing();
});

var cosFunc = document.getElementById("cos");
cosFunc.addEventListener('change', function() {
    if (this.checked) {
        cosOn = true;
    } else {
        cosOn = false;        
    }
    plotGraphing();
});

function stopPlotting() {
    source.close();
    plottingOn = false;
}