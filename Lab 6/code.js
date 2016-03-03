$(document).ready( function () 
{
	$('#p1').click(function()
	{
		$(this).hide();
		
	});

	$('#p3').dblclick(function()
	{
		$(this).hide();
		
	});
	
	$('#p4').hover(function()
	{
		$(this).css({'color' : 'purple', 'font-size' : '20px'})
		
	},
	function()
	{
		$(this).css({'color' : 'black', 'font-size' : '12px'})
	});
	
	$('#text').focus(function()
	{
		$(this).css({'background-color': 'yellow'});
		
	});
	
	$('#text').blur(function()
	{
		$(this).css({'background-color': 'green'});
		
	});
	
	$('#button1').click(function()
	{
		$('#div1').fadeIn(1000);
	});
	
	$('#button2').click(function()
	{
		$('#p2').show();
	});
	
	$('#button3').click(function()
	{
		$('#div2').fadeOut(1000);
	});
	
	$('#button4').click(function()
	{
		$('#panel').slideToggle("slow");
	});

	/*$('.text').hover(function()
	{
		$(this).css({'color': 'red', 'font-weight': 'bold'});
	}, 
	function()
	{
		$(this).css({'color': 'black', 'font-weight': 'normal'});
	});*/
	$('#panel2').click(function()
	{
		$(this).css({'background-color': 'green', 'font-size': '40px', 'font-weight': 'bold', 'color' : 'orange', 'padding': '75px'});
		
	});

});