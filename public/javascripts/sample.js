 $(document).ready(function(){
      var i=1;
     $("#add_row").click(function(){
      $('#addr'+i).html("<td>"+ (i+1) +"</td><td><input name='name"+i+"' type='text' placeholder='Name' class='form-control input-md'  /> </td><td><input  name='mail"+i+"' type='text' placeholder='Mail'  class='form-control input-md'></td><td><input  name='mobile"+i+"' type='text' placeholder='Mobile'  class='form-control input-md'></td>");

      $('#tab_logic').append('<tr id="addr'+(i+1)+'"></tr>');
      i++;
  });
     $("#delete_row").click(function(){
    	 if(i>1){
		 $("#addr"+(i-1)).html('');
		 i--;
		 }
	 });

	 $("#add_config").on('click',function(e){
	    alert('saving config');
	 });

});

function addConfig() {
  var elemId = document.getElementById('key-id').value;
  var elemValue = document.getElementById('value-id').value;

  const entries = new Map([[elemId, elemValue]]);

  const obj = Object.fromEntries(entries);

  const conf = JSON.stringify(obj);
  window.alert(conf);

}
