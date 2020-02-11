$(document).ready(function(){
  $("#id").click(function(){
    htmlobj=$ajax({url:"",async:false});
    $("#empty-div").html(htmlobj.responseText)
    })
})
