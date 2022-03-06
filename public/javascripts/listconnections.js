// The plugin
$.fn.json_beautify= function() {
    try {
        var obj = JSON.parse( this.val() );
        var pretty = JSON.stringify(obj, undefined, 4);
        this.val(pretty);
    } catch (err) {
        alert("invalid json");
    }
};

// prettify textarea json in connection config edit
//$('#txt-edit-connection-config').json_beautify();
function defaultConfig() {
  const obj = { url: "url", apiUser: "api key", apiSecret: "api secret" };
  const conf = JSON.stringify(obj);
  return conf
}
// add default values in config
$("#txt-add-connection-config").val(defaultConfig());

function beautifyAdd() {
  $('#txt-add-connection-config').json_beautify();
}

function beautifyEdit() {

  $('#txt-edit-connection-config').json_beautify();
}

function isValidJSONString(str) {
    try {
        JSON.parse(str);
    } catch (e) {
        return false;
    }
    return true;
}

$('form#edit-connection-form').submit(function(e){
    var txt = $("#txt-edit-connection-config").val();
    if (!isValidJSONString(txt)) {
        e.preventDefault();
        alert('invalid json config');
    }
});

$('form#add-connection-form').submit(function(e){
    var txt = $("#txt-add-connection-config").val();
    if (!isValidJSONString(txt)) {
        e.preventDefault();
        alert('invalid json config');
    }
});

function addConfig() {
  var elemId = document.getElementById('key-id').value;
  var elemValue = document.getElementById('value-id').value;

  if (elemId == "") {
    alert("key can not be empty");
    return;
  }

  const entries = new Map([[elemId, elemValue]]);

  const newObj = Object.fromEntries(entries);


  var allConf = document.getElementById('txt-add-connection-config').value;

  var oldObj = JSON.parse(allConf);

  const combinedObj = { ...oldObj, ...newObj };

  const newConf = JSON.stringify(combinedObj);

  $("#txt-add-connection-config").val(newConf);

  beautifyAdd()

}

function addConfigUpdate() {
  var elemId = document.getElementById('key-id').value;
  var elemValue = document.getElementById('value-id').value;

  if (elemId == "") {
    alert("key can not be empty");
    return;
  }

  const entries = new Map([[elemId, elemValue]]);

  const newObj = Object.fromEntries(entries);


  var allConf = document.getElementById('txt-edit-connection-config').value;

  var oldObj = JSON.parse(allConf);

  const combinedObj = { ...oldObj, ...newObj };

  const newConf = JSON.stringify(combinedObj);

  $("#txt-edit-connection-config").val(newConf);

  beautifyEdit()

}

$(document).ready(function(){

  var allConf = document.getElementById('txt-add-connection-config').value;

  const confObj = JSON.parse(allConf);

  $('#dynamic-config').append('<h5>Config</h5>');

  const isObject = (value) => {
    return !!(value && typeof value === "object" && !Array.isArray(value));
  };

  var formStart = '<form action="/connections" method="POST">'
  var formEnd = '</form>'

  for (const [key, value] of Object.entries(confObj)) {

    var label = '<div class="form-group row">' +
    '<label for="'+(key+"-id")+'" class="col-sm-1 col-form-label">'+key+'</label>' +
    '<div class="col-sm-4">' +
    '<input type="text" class="form-control" id="'+(key+"-id")+'" value="'+value+'" />' +
    '</div>' +
    '</div>';
    if (!isObject(value)) {
       $('#dynamic-config').append(label);
    }

    //TODO make recursive call to load the key value pair

  }

});
