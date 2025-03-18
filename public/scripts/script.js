
function init() {
}

$( () => {
  init();
});

$("#submitButton").click(() => {
  $.ajax(
    "/submit",
    {
      type: "GET",
      processData: true,
      data: {
        text: $("#textInput").val(),
        key: $("#keyInput").val(),
        method: $("#methodInput").val(),
      },
      dataType: "json",
      success: function (e) { 
        $("#textInput").val("");
        $("#keyInput").val("");
        $("#methodInput").val("");
        $("#responserows").append(`<tr><td>${e.plaintext}</td><td>${e.encryptedText}</td><td>${e.method}</td><td>${e.key}</td></tr>`)
      },
      error: function (jqXHR, textStatus, errorThrown) {
        alert("Error: " + jqXHR.responseText);
        alert("Error: " + textStatus);
        alert("Error: " + errorThrown);
      }
    }
  );
});