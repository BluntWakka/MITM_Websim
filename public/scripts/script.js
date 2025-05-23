
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
        attackerCount: $("#attackerInput").val(),
        gpu: $("#gpuInput").val()
      },
      dataType: "json",
      success: function (e) { 
        $("#textInput").val("");
        $("#keyInput").val("");
        $("#responserows").append(`<tr><td>${e.plaintext}</td><td>${e.encryptedText}</td><td>${e.method}</td><td>${e.key}</td><td>${e.attacker}</td><td>${e.gpu}</td><td>${e.entropy}</td><td>${e.factor}</td><td>${e.time}</td></tr>`)
      },
      error: function (jqXHR, textStatus, errorThrown) {
        alert("Error: " + jqXHR.responseText);
        alert("Error: " + textStatus);
        alert("Error: " + errorThrown);
      }
    }
  );
});