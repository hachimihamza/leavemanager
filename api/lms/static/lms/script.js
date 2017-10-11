$( document ).ready(function() {
  

/*
$("a[href^='#/']").each(function(link){
	href = $(this).attr("href");
	view = href.slice(2);

	Path.map(href).to(function(){
  	 	$( "#dynamic" ).load(view);
	});
	console.log(href + " " +view);
});
*/

Path.root("#/about");
Path.listen();

});