import { not } from '@angular/compiler/src/output/output_ast';
import { Component, ViewChild } from '@angular/core';
import { IonItemOption, IonItemOptions, IonList } from '@ionic/angular';
import { Button } from 'protractor';
import { isNumber } from 'util';



export class HomePage {
  year:number;
  Age(){
    let age:number = 2021 - this.year;
    if (!isNaN(age))
    {
        return age
    }
    else{
    }
  }



}
