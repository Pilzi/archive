
function onClickSubmitButton(){
    inputHint = document.getElementById("inputHint")
    var streamerName = document.getElementById("textInput").value.toLowerCase()


    if (streamerName.length <= 3){
        inputHint.innerHTML = ("each Twitch username must be between 4 and 25 characters")
        return
    }
    
    chrome.storage.local.get('streamer', function (streamer) {

        if (!Object.hasOwn(streamer, "streamer")){
            streamer = {
                "streamer": []
            }
        }
        try{
            var isStreamerAlreadyInList = streamer.streamer.map((x) => x.name == streamerName)
        }catch(TypeError){
            var isStreamerAlreadyInList = [false]
        }
        
        

        if (isStreamerAlreadyInList.includes(true)){
            inputHint.innerHTML = (`${streamerName} has already been added`)
            return
        }else{
            streamer.streamer.push({
                "name": streamerName,
                "startedAt": "",
                "notification": true,
                "key": streamer.streamer.length
            })
            
            streamer.streamer.map((streamerContainer, index) => {
                streamer.streamer[index].key = index
            })
            createContainer(streamerName, true, streamer, streamer.streamer.length)
              
        }
        chrome.storage.local.set(streamer)
        //alert(JSON.stringify(streamer))
        inputHint.innerHTML = ""
        document.getElementById("textInput").value = "";
    }); 
}


function createContainer(streamerName, notification, streamer, key){
    var View = document.getElementById("streamerView");
    var rowdiv = document.createElement("div");
    var hyperlinkContainer = document.createElement("a")
    var streamerContainer = document.createElement("div");
    var notificationButton = document.createElement("button");
    var deleteButton = document.createElement("button");

    rowdiv.className = "row borderTop"
    rowdiv.id =  key;

    //hyperlinkContainer.href = "stats/stats.html"
    hyperlinkContainer.className = "hyperlinkContainer"

    //streamerContainer.className = "streamerContainer"
    streamerContainer.innerHTML = streamerName
    
    notificationButton.className = "viewButton"
    notificationButton.type = "button"
    notificationButton.style.backgroundColor = notification ? "#46ff55":"#212121"
    

    deleteButton.className = "viewButton"
    deleteButton.type = "button"
    deleteButton.style.backgroundColor = "#ff0d00"

    hyperlinkContainer.appendChild(streamerContainer)
    rowdiv.appendChild(hyperlinkContainer);
    rowdiv.appendChild(notificationButton);
    rowdiv.appendChild(deleteButton);
    View.appendChild(rowdiv);

    hyperlinkContainer.addEventListener("click", () => {
        chrome.storage.local.set({"streamerName": streamerName})
        window.location.href= "stats/stats.html";
    })

    notificationButton.addEventListener("click", () => {
       
        if (window.getComputedStyle(notificationButton).backgroundColor == "rgb(33, 33, 33)"){
            notificationButton.style.backgroundColor = "#46ff55"
            streamer.streamer[key].notification = true
        }else{
            notificationButton.style.backgroundColor = "#212121" 
            streamer.streamer[key].notification = false
            streamer.streamer[key].startedAt = ""
        }
        chrome.storage.local.set(streamer)
    })

    deleteButton.addEventListener("click", () => {
        document.getElementById(key).remove();

        chrome.storage.local.get('streamer', function (streamerContainer) {
            var updatedStreamers = streamerContainer.streamer.filter(item => item.key !== key);
            streamerContainer.streamer = updatedStreamers;

            chrome.storage.local.set(streamerContainer);
            
        });
    })
}

document.getElementById("submitButton").addEventListener("click", onClickSubmitButton)


addEventListener("load", (event) => {
    try{
        chrome.storage.local.get('streamer', function (streamer) {
            if (!streamer.streamer) {
                return;
            }

            streamer.streamer.map((streamerContainer, index) => {
                
                streamer.streamer[index].key = index
                chrome.storage.local.set(streamerContainer);

                createContainer(streamerContainer.name, streamerContainer.notification, streamer, index)
            })
        })
    }catch (TypeError){
        console.log("empty list: " + TypeError)
    }
});

var textInput = document.getElementById("textInput")
textInput.addEventListener("keyup", function(event) {
    if (event.key === "Enter") {
        document.getElementById("submitButton").click()
    }
});
