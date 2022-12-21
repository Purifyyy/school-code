class amplitudeSelector extends HTMLElement {
    connectedCallback() {
        this.innerHTML = `<div class="row row-cols-2">
                            <div class="col">
                                <input type="checkbox" id="slider" class="sliderBox" name="slider" value="slider" checked>
                                <label for="slider" class="lab">Posuvník</label>
                            </div>
                            <div class="col">
                                <div id="sliderArea"><input type="range" min="1" max="15" value="1" class="slider" id="rangeSlider"><div id="sliderValue">1</div></div><br>
                            </div>
                            <div class="col">
                                <input type="checkbox" id="input" class="inputBox" name="input" value="input" checked>
                                <label for="input" class="lab">Textové pole</label>
                            </div>
                            <div class="col">
                                <input type="number" class="amp" name="amplitude" min="1" max="15" id="ampInput" value="1"></input>
                            </div>
                          </div>
                          <style>
                            .slider {
                                -webkit-appearance: none;
                                height: 32px;
                                width: 20vw;
                                background: white;
                                outline: solid 2px white;
                                border: solid 2px black;
                                -webkit-transition: .2s;
                                transition: opacity .2s;
                            }
                            
                            .slider::-webkit-slider-thumb {
                                -webkit-appearance: none;
                                appearance: none;
                                width: 30px;
                                height: 32px;
                                background: black;
                                cursor: pointer;
                            }
                            
                            .amp {
                                height: 33px;
                                width: 20vw;
                                font-weight: bold;
                                font-size: 120%;
                            }
                            
                            #sliderValue {
                                font-size: 120%;
                            }

                            .row:first-child {
                                margin-bottom: 10vh !important;
                            }
                          <style>`;
        this.querySelector('input.sliderBox').addEventListener('change', event => this.onSliderBoxChange(event));
        this.querySelector('input.inputBox').addEventListener('change', event => this.onInputBoxChange(event));
        this.querySelector('input.slider').addEventListener('input', event => this.onSliderValueChange(event));
        this.querySelector('input.amp').addEventListener('input', event => this.onInputValueChange(event));
    }
    onSliderBoxChange(event) {
        var element = document.getElementById("sliderArea");
        if(event.target.checked){
            element.style.visibility = "visible";
        } else {
            element.style.visibility = "hidden";
        }
    }
    onInputBoxChange(event) {
        var element = document.getElementById("ampInput");
        if(event.target.checked){
            element.style.visibility = "visible";
        } else {
            element.style.visibility = "hidden";
        }
    }
    onSliderValueChange(event) {
        document.getElementById("sliderValue").innerHTML = event.target.value;
        document.getElementById("ampInput").value = event.target.value;
    }
    onInputValueChange(event) {
        if(event.target.value <= 0) {
            event.target.value = 1;
        } else if(event.target.value > 15) {
            event.target.value = 15;
        }
        document.getElementById("sliderValue").innerHTML = event.target.value;
        document.getElementById("rangeSlider").value = event.target.value;
    }
}

if(!customElements.get('amp-selector')) {
    customElements.define('amp-selector', amplitudeSelector);        
}