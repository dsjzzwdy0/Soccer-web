<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

<%
	String mid = request.getParameter("mid");
%>
<script type="text/javascript" src="content/plugins/echarts/echarts.js"></script>
<style>
.container{
	width: 1300px;
	margin: auto;
}
.main #opseq
{
	width: 100%;
	height: 600px;
	margin: auto;
}

.graphtool{
	margin-left: 120px;
	text-align: left;
}

#datatype{
	width: 120px;
	height: 22px;
}

</style>

<%@include file="./match/matchtoolbar.jsp"%>

<div class="main">
	<div class="container">
		<div class="graphtool">
			<span>显示数据</span><span>
				<select id="datatype" onchange="changeView()">
					<option value="odds" selected>赔率</option>
					<option value="prob">概率</option>
				</select>
			</span>		
			<span>记录条数：</span><span><input type="text" id="oddsnum"/></span>
		</div>
		<div id="opseq"></div>
	</div>
</div>

<script type="text/javascript">
var url = "odds/getMatchSequenceOps";
var params = {mid: '<%=mid%>'};
var matchData = null;

var chart;
$(document).ready(function(){
	chart = echarts.init(document.getElementById('opseq'),'shine');
	getData(params);	
	$("#btnRefresh").click(function(){
		refreshData();
	});
	
	$("#btnUnchecked").click(function(){
		$("input:checkbox").removeAttr("checked");
	});
	window.onresize = function () {
		chart.resize();
    }
});

function refreshData()
{
	var str = "";
	var size = 0;
	$('.checkPanel').find(':checkbox').each(function(){
		if ($(this).is(":checked"))
		{
			str += $(this).val() + ",";
			size ++;
		}
	});
	
	if(size != 2)
	{
		layer.alert("您选了" + size + "个字段，请选择2个字段");
		return;
	}
	//chart.showLoading();
	var params = 
	{
		fields: str
	};
	getData(params);
}

function getData(params)
{
	$.ajax({ 
		url: "odds/getMatchSequenceOps",
		data: params,
		context: document.body, 
		success: function(json)
		{
			//layer.msg("下载数据成功", {time: 2000}) 
			matchData = json.data;
			showData();
		},
	});
}

function changeView()
{
	showData();
}

function createItem(datas, rec)
{
	var win = rec.odds[0];
	var draw = rec.odds[1];
	var lose = rec.odds[2];
	
	var t = 1.0 / (1.0 / win + 1.0 /draw + 1.0 / lose);
	var winprob = t / win;
	var drawprob = t / draw;
	var loseprob = t / lose;
	
	var item = [];
	item[0] = rec.opentime;
	item[1] = winprob;
	item[2] = rec.corpid;
	datas[0].push(item);
	
	item = [];
	item[0] = rec.opentime;
	item[1] = drawprob;
	item[2] = rec.corpid;
	datas[1].push(item);
	
	item = [];
	item[0] = rec.opentime;
	item[1] = loseprob;
	item[2] = rec.corpid;
	datas[2].push(item);
	
}

function showData()
{
	if($.isNullOrEmpty(matchData))
	{
		layer.msg("没有数据，不进行显示", {time: 2000});
		return;
	}
	var datas = [[], [], []];
	var dataname = []
	var type = $('#datatype').val();
	
	
    var list = matchData.list;  
    var len = list.length;
	
    $('#oddsnum').val(len);
    var rec;
    var min = 1000
    var max = -10
    
    if(type == 'prob')
    {
    	dataname = ['胜率', '平率', '负率']
    	min = 0;
		max = 1.0;
    	for(var i = 0; i < len; i ++)
        {
        	rec = list[i];
        	createItem(datas, rec);
        }
    }
    else
    {
    	dataname = ['胜赔', '平赔', '负赔']
    	for(var i = 0; i < len; i ++)
        {
    		rec = list[i];
        	for(var j = 0; j < 3; j ++)
            {
            	var item = [];
            	item[0] = rec.opentime;
            	item[1] = rec.odds[j];
            	item[2] = rec.corpid;
            	
            	if(min > rec.odds[j])
                	min = rec.odds[j]
                if(max < rec.odds[j])
                	max = rec.odds[j]
            	
            	datas[j].push(item);
        	}
        }
    	
    	max = Math.floor(max) + 1;
    	min = Math.floor(min) - 1;
    	if(min < 0) min = 0;
    }
    
	
    option = {
        xAxis: {
            type: 'time',
            name: '时间轴',
        },
        yAxis: {
            type: 'value',
            name: '赔率值',
            max: max,
            min: min,
        },
        tooltip: {  
            /*返回需要的信息*/  
            formatter: function(param) {  
                var value = param.value;  
                return '<div style="border-bottom: 1px solid rgba(255,255,255,.3); font-size: 16px;padding-bottom: 7px;margin-bottom: 7px;"> ' 
                	+ formatDate(new Date(value[0]), 'yyyy-MM-dd hh:mm:ss') + ': ' + '(' + value[1] + ') corpid ='  + value[2] + '</div>';  
            }  
        },
        legend: {  
            orient: 'vertical',    
            data: dataname,
            show: true,
            y: 'top',
            x: 'right',
            z:  1,
        }, 
        toolbox: {  
            show: true,  
            orient: 'vertical',  
            left: 'right',  
            top: 'center',  
            feature: {  
                dataView: {readOnly: false},  
                restore: {},  
                saveAsImage: {}  
            }  
        },  
        series: [{
            name: dataname[0],
            data: datas[0],
            type: 'scatter',
            symbolSize: 4,
            itemStyle: {
            	normal: {color: 'red'}
            }
        },
        {
            name: dataname[1],
            data: datas[1],
            type: 'scatter',
            symbolSize: 4,
            itemStyle: {
            	normal: {color: 'green'}
            }
        },
        {
            name: dataname[2],
            data: datas[2],
            type: 'scatter',
            symbolSize: 4,
            itemStyle: {
            	normal: {color: 'blue'}
            }
        }]
    };

    chart.setOption(option);

}
</script>