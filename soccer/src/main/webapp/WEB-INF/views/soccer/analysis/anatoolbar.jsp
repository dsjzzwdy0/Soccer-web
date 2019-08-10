<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="lqnav" style="position: relative; margin-bottom: 0px; border-bottom: none; background-color: #fafafa;">
	<div style="float: left;">
		<span class="location">当前位置：</span>
		<a href="../" class="aco">东彩首页</a> 
		<span>&gt;</span> 
		<a href="analysis" class="aco">数据比较分析</a>
		<span>&gt;</span>  
		<b> ${title } <c:if test="${league!=null}"> (&nbsp;<a href="league?lid=${lid}" class="league">${league.name }</a>&nbsp;)</c:if></b>
	</div>
</div>

<div class="top-chosse">
	<div class="top-plnav">
		<ul>
			<li class="n_1">
				<a href="
					<c:if test="${page=='anarel'}">javascript:void(0);</c:if>
					<c:if test="${page!='anarel'}">analysis?type=anarel<c:if test="${not empty lid}">&lid=${lid}</c:if>
					</c:if>" 
					title="欧赔数据关联分析" class="<c:if test="${page=='anarel'}">cur</c:if>">关联分析
				</a>
			</li>
			<li class="n_2"><a href="<c:if test="${page=='anaoy'}">javascript:void(0);</c:if><c:if test="${page!='anaoy'}">analysis?type=anaoy<c:if test="${not empty lid}">&lid=${lid}</c:if></c:if>" title="战绩情况对比" class="<c:if test="${page=='anaoy'}">cur</c:if>">战绩情况对比</a></li>
			<li class="n_3"><a href="<c:if test="${page=='anaop'}">javascript:void(0);</c:if><c:if test="${page!='anaop'}">analysis?type=anaop<c:if test="${not empty lid}">&lid=${lid}</c:if></c:if>" title="欧赔对比" class="<c:if test="${page=='anaop'}">cur</c:if>">欧赔对比</a></li>
			<li class="n_4"><a href="<c:if test="${page=='anayp'}">javascript:void(0);</c:if><c:if test="${page!='anayp'}">analysis?type=anayp<c:if test="${not empty lid}">&lid=${lid}</c:if></c:if>" title="亚盘对比" class="<c:if test="${page=='anayp'}">cur</c:if>">亚盘对比</a></li>
			<li class="n_5"><a href="<c:if test="${page=='anafc'}">javascript:void(0);</c:if><c:if test="${page!='anafc'}">analysis?type=anafc<c:if test="${not empty lid}">&lid=${lid}</c:if></c:if>" title="欧赔方差分析" class="<c:if test="${page=='anafc'}">cur</c:if>">方差分析</a></li>
			<li class="n_6"><a href="<c:if test="${page=='anazj'}">javascript:void(0);</c:if><c:if test="${page!='anazj'}">analysis?type=anazj<c:if test="${not empty lid}">&lid=${lid}</c:if></c:if>" title="战绩对比分析" class="<c:if test="${page=='anazj'}">cur</c:if>">战绩分析</a></li>
			<li class="n_7"><a href="<c:if test="${page=='anazh'}">javascript:void(0);</c:if><c:if test="${page!='anazh'}">analysis?type=anazh<c:if test="${not empty lid}">&lid=${lid}</c:if></c:if>" title="综合分析" class="<c:if test="${page=='anazh'}">cur</c:if>">综合分析</a></li>
		</ul>
	</div>
	<div class="sx_form_c pl-chosse-cont">
		<select id="typeSel" class="sel_list">
			<option value="bd" selected="selected">北单数据</option>
			<option value="jc">竞彩数据</option>
		</select> 
		<select id="issueSel" class="sel_list">
			<c:forEach items="${issues }" var="issue" varStatus="status">
				<option value="${issue }" <c:if test="${status.count == 1}"> selected="selected" </c:if> >${issue}</option>
			</c:forEach>
		</select>
		<select id="settingSel" class="sel_list" style="display: none;">
			<c:forEach items="${settings }" var="setting" varStatus="status">
				<option value="${setting.sid }" >${setting.name}</option>
			</c:forEach>
		</select>
		<div class="pl-wind-b game_select" id="ssxz">
			赛事选择
			<div class="pl-wind-ss" style="display: none;">
				<div class="pl-wind-b1">赛事选择</div>
				<div style="clear:both;"></div>
				<div class="pl-wind-cont">
					<ul class="ssxz-ul" style="display: none;">
						<li id="leagueList">
						</li>
						<div style="clear: both;"></div>
						<li class="pl-wind-btn sx_form_b">
							<input value="全选" type="button" id="btnFull">
							<input value="反选" type="button" id="btnRevert">
							<input value="全不选" type="button" id="btnNone">
						</li>
					</ul>
					<div class="clear"></div>
				</div>
			</div>
		</div>
		
		<div style="float:left; margin-left:3px;">
			共 <span id="matchNumAll">10</span>场比赛，隐藏了 <span id="matchNumHide">0</span>场。
			<a href="javascript:;" style="display: none" id="recover">恢复</a>
		</div>

		<div id="newToolbar" class="newToolBar" style="display: none; float: right; margin-right: 10px;">
			<div class="check_same_league" style="float: left;">
				<input class="sel_list" id="sameLeague" style="margin-right: 4px; margin-top: auto; display:none;" type="checkbox" checked="true" />同联赛内比较
			</div>
			<select id="oddsType" class="sel_list" style="width:70px; display:none;">
				<option value="start" selected>初盘</option>
				<option value="now">即时</option>
			</select>
			<select id="sort" class="sel_list" style="width: 70px; display:none;">
				<option value="asc" selected>升序</option>
				<option value="desc">降序</option>
			</select>
			<select id="threshold" class="sel_list" title="关联差值间隔" style="width: 70px; margin-right: 15px;" data-style="btn-warning">
				<option value="0.01">0.01</option>
				<option value="0.02" selected>0.02</option>
				<option value="0.03">0.03</option>
				<option value="0.04">0.04</option>
				<option value="0.05">0.05</option>
				<option value="0.08">0.08</option>
				<option value="0.10">0.10</option>
			</select>
			
			<div style="display: inline-block; float: left;">选择赔率公司</div>
			<select id="comps-multi-select" multiple="multiple">
				<optgroup label="欧赔">
				    <option value="1" selected="selected">平均欧赔</option>
				    <option value="2" selected="selected">Fonbet</option>
				    <option value="3" selected="selected">Missonjon</option>
			    </optgroup>
			    <optgroup label="亚盘">
				    <option value="5">Fonbet</option>
			    </optgroup>
			</select>
			<input type="button" class="pl-topbtn" id="btnConfigure" value="排 序"/>
			<input type="button" class="pl-topbtn" id="btnReUnion" value="重 置"/>		
		</div>
	</div>
</div>
<script type="text/javascript">
var stateListeners = new StateListeners();
var complabel = '赔率公司选择';
function showNewToolBar()
{
	$('.top-chosse #newToolbar').show();
}

function showOddsType()
{
	$('.top-chosse #oddsType').show();
	$('.top-chosse #sameLeague').show();
	$('.top-chosse #sameLeague').attr("checked", 'checked');;
}
function showSettingSel()
{
	$('.top-chosse #settingSel').show();
}

/**
 * 初始化联赛控制面板
 * @param matches
 * @returns
 */
function initLeaguePanel(matches)
{
	var leagueRecs = [];
	
	function LeagueRec(lid, name)
	{
		this.lid = lid;
		this.name = name;
		this.num = 1;
		
		this.addNum = function(){ this.num ++; }
	}
	
	function addLeagueRec(lid, name)
	{
		var len = leagueRecs.length;
		for(var i = 0; i < len; i ++)
		{
			var l = leagueRecs[i];
			if(l.lid == lid)
			{
				l.addNum();
				return true;
			}
		}
		var l = new LeagueRec(lid, name);
		leagueRecs.push(l);
		return true;
	}
	
	var len = matches.length;
	for(var i = 0; i < len; i ++)
	{
		var m = matches[i];
		addLeagueRec(m.lid, m.leaguename);
	}
	
	len = leagueRecs.length;
	var html = [];
	for(var i = 0; i < len; i ++)
	{
		var rec = leagueRecs[i];
		html.push('<label><input name="CheckboxGroup1" value="' + rec.lid + '" type="checkbox" checked>');
		html.push('<em class="echao" style="background-color: rgb(102, 153, 0)">' + rec.name + '</em>[' + rec.num + ']');
		html.push('</label>');		
	}
	
	$('.game_select #leagueList').html(html.join(''));
}


//获得页面的配置信息
function getConfValue()
{
	var matchtype = $('#typeSel').val();
	var oddstype = $('#oddsType').val();
	var issue = $('#issueSel').val();
	var sid = $('#settingSel').val();
	var sort = $('#sort').val();
	var compIds = $('#comps-multi-select').val();
	var threshold = Number($('#threshold').val());
	var sameleague = $('#sameLeague').prop('checked');
	var showOrdinary = $('#showOrdinary').prop('checked');
	
	if($.isNullOrEmpty(threshold))
	{
		threshold = 0.02;
	}
	
	//过滤的数据
	var oLeagueList = $('#leagueList input');
	var lids = [];
	oLeagueList.each(function(){
		if(!$(this).prop("checked"))
	    {
	      	lids.push($(this).val());
	    }
	});
	return {
		//field: field,
		//gid: gid, 
		//index: index,
		type: matchtype,
		issue: issue,
		sid: sid,
		first: oddstype == 'now' ? false : true,
		sort: "desc" == sort ? false : true,
		threshold: threshold,
		sameLeague: sameleague,
		showOrdinary: showOrdinary,
		lids: lids,
		showCompIds: compIds,
		isCompShow: function(id)
		{
			var len = this.showCompIds.length;
			for(var i = 0; i < len; i ++)
			{
				if(this.showCompIds[i] == id) return true;
			}
			return false;
		}
	};
}

function createSelectOpts(label, comps)
{
	var selecthtml = [];
	var size = comps.length;
	selecthtml.push('<optgroup label="' + label + '">');
	for(var i = 0; i < size; i ++)
	{
		var comp = comps[i];    		
		selecthtml.push('<option value="' + comp.id + '" selected="selected">' + comp.name + '</option>');
	}
	selecthtml.push('</optgroup>');
	return selecthtml.join(' ');
}

function createDropdownSelects(label, comps)
{
	var dropdownhtml = [];
	var size = comps.length;
	dropdownhtml.push('<div class="fs-options"><div class="fs-optgroup">');
	dropdownhtml.push('<div class="fs-optgroup-label">' + label + '</div>');
	for(var i = 0; i < size; i ++)
	{
		var comp = comps[i];
		dropdownhtml.push('<div class="fs-option selected" data-value="' + comp.id + '"><span class="fs-checkbox"><i></i></span>');
		dropdownhtml.push('<div class="fs-option-label">' + comp.name + '</div></div>');
	}
	dropdownhtml.push('</div></div>');
	return dropdownhtml.join(' ');
}

function updateCompsSelect(compSetting)
{
	var selecthtml = [];
	var drophtml = [];
	var opcomps = compSetting.getOpCorps();
	if(opcomps.length > 0)
	{
		var label = '欧赔';
		selecthtml.push(createSelectOpts(label, opcomps));
		drophtml.push(createDropdownSelects(label, opcomps));
	}
	var ypcomps = compSetting.getYpCorps();
	if(ypcomps.length > 0)
	{
		var label = '亚盘';
		selecthtml.push(createSelectOpts(label, ypcomps));
		drophtml.push(createDropdownSelects(label, ypcomps));
	}
	var select = $('#comps-multi-select');
	var t = $(select).parent();
	$(t).find('.fs-label').html(complabel);
	$(t).find('.fs-dropdown').html(drophtml.join(''));
	$(select).html(selecthtml.join(''));
}

//选择的数据
$(function(){
	$(document).on('click','.game_select',function(){
		$(this).children('.pl-wind-ss').show();
		$(this).find('ul').show();
	}).on('mouseleave','.game_select',function(){
		$(this).children('.pl-wind-ss').hide();
		$(this).find('ul').hide();
	});
	
	$('.top-chosse .sel_list').on('change', function(){
		//layer.msg('State has changed.' + $(this).attr('id'));
		var conf = getConfValue();
		stateListeners.notify('change', this, conf);
	});
	
	$('#leagueList').on("click", "input", function(){
		var conf = getConfValue();
		stateListeners.notify('change', this, conf);
	});
	$(".sx_form_b input").on("click", function(){
        var oMatchList = $("#leagueList input");
        switch($(this).val()){
            case "全选":
                oMatchList.prop("checked", true);
                break;
            case "反选":
                oMatchList.each(function(){
                    $(this).prop("checked") ?  $(this).prop("checked", false) : $(this).prop("checked", true);
                })
                break;
            case "全不选":
                oMatchList.prop("checked", false);
                break;
            default: ;
        };
        var conf = getConfValue();
		stateListeners.notify('change', this, conf);
    });
	

    $('#comps-multi-select').fSelect({
    	placeholder: complabel,
    	showSearch: false,
    	numDisplayed: 0,
        overflowText: '已选择{n}公司',
    });
    
    $('#btnConfigure').on('click', function(){
    	conf = getConfValue();
    	alert(conf.isCompShow('0'));
    });
    
    /*
    $('#btnReUnion').on('click', function(){
    	var select = $('#comps-multi-select');
    	//$(select).removeClass('hidden');
    	var t = $(select).parent();
    	$(t).find('.fs-label').html('赔率公司选择');
    	
    	var dropdownhtml = [];
    	var selecthtml = [];
    	
    	dropdownhtml.push(createDropdownSelects('欧赔', opcomps));
    	dropdownhtml.push(createDropdownSelects('亚盘', ypcomps));
    	
    	selecthtml.push(createSelectOpts('欧赔', opcomps));
    	selecthtml.push(createSelectOpts('亚盘', ypcomps));
    	
        $(t).find('.fs-dropdown').html(dropdownhtml.join(''));
    	$(select).html(selecthtml.join(''));
    });*/
})
</script>