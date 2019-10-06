import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { ActivatedRoute, Router } from '@angular/router';
import { Account, LoginModalService, Principal } from '../shared';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.css'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
 context: any;
   canvas: any;
    grid: any;
    count: any;
    snake: any;
    apple: any;


    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        protected router: Router,
    ) {

    }
    constB(){

      this.canvas = document.getElementById('game');
        this.context = this.canvas.getContext('2d');
        this.grid=16;
        this.count=0;
        this.snake={
            x: 0,
            y: 0,

            // snake velocity. moves one grid length every frame in either the x or y direction
            dx: 16,
            dy: 0,

            // keep track of all grids the snake body occupies
            cells: [],

            // length of the snake. grows when eating an apple
            maxCells: 4
        };
        this.apple={
            x: 320,
            y: 320
        }

    }
    getRandomInt(min, max) {
        return Math.floor(Math.random() * (max - min)) + min;
    }
    loop() {
        this.diChuyen();
        requestAnimationFrame(() => this.loop());

        if (++this.count < 14) {
            return;
        }
        this.count = 0;
        this.context.clearRect(0,0,this.canvas.width,this.canvas.height);
        document.addEventListener('keydown', (e: KeyboardEvent) => {
            // prevent snake from backtracking on itself by checking that it's
            // not already moving on the same axis (pressing left while moving
            // left won't do anything, and pressing right while moving left
            // shouldn't let you collide with your own body)

            // left arrow key
            if (e.keyCode === 37 ) {
                this.snake.dx = -this.grid;
                this.snake.dy = 0;
            }
            // up arrow key
            else if (e.keyCode === 38 ) {
                this.snake.dy = -this.grid;
                this.snake.dx = 0;
            }
            // right arrow key
            else if (e.keyCode === 39 ) {
                this.snake.dx = this.grid;
                this.snake.dy = 0;
            }
            // down arrow key
            else if (e.keyCode === 40 ) {
                this.snake.dy = this.grid;
                this.snake.dx = 0;
            }
        });
        // slow game loop to 15 fps instead of 60 (60/15 = 4)
       // this.canvas = document.getElementById('game');
        //this.context = this.canvas.getContext('2d');
    //    if (++this.count < 4) {
      //      return;
       // }
        this.count = 0;
       // this.context.clearRect(0,0,this.canvas.width,this.canvas.height);
        // move snake by it's velocity
        this.snake.x += this.snake.dx;
        this.snake.y += this.snake.dy;
        this.snake.cells.unshift({x:this.snake.x,y:this.snake.y});
        if(this.snake.x>=this.canvas.width){
            this.snake.x=0;
        }
        if(this.snake.x<0){
            this.snake.x=this.canvas.width;
        }

        if(this.snake.y>=this.canvas.height){
            this.snake.y=0;
        }
        if(this.snake.y<0){
            this.snake.y=this.canvas.height;
        }
        /**
        // wrap snake position horizontally on edge of screen
        if (this.snake.x < 0) {
            this.snake.x = this.canvas.width - this.grid;
        }
        else if (this.snake.x >= this.canvas.width) {
            this.snake.x = 0;
        }

        // wrap snake position vertically on edge of screen
        if (this.snake.y < 0) {
            this.snake.y = this.canvas.height - this.grid;
        }
        else if (this.snake.y >= this.canvas.height) {
            this.snake.y = 0;
        }
        // keep track of where snake has been. front of the array is always the head
        this.snake.cells.unshift({x: this.snake.x, y: this.snake.y});
        // remove cells as we move away from them
        if (this.snake.cells.length > this.snake.maxCells) {
            this.snake.cells.pop();
        }
         */
        // draw apple
        if (this.snake.cells.length > this.snake.maxCells) {
            this.snake.cells.pop();
        }
        this.context.fillStyle = 'red';
        this.snake.cells.forEach((cell,index)=>{
            this.context.fillRect(cell.x, cell.y, 15, 15);
        });


        this.context.fillStyle = 'blue';
        this.context.fillRect(100, 160,15,15);
        // draw snake one cell at a time
        this.context.fillStyle = 'green';
       // this.snake.cells.forEach(function(cell) {

            // drawing 1 px smaller than the grid creates a grid effect in the snake body so you can see how long it is
         //   this.context.fillRect(cell.x, cell.y, this.grid-1, this.grid-1);

      //  this.context.clearRect(0,0,this.canvas.width,this.canvas.height);
//        })
}


diChuyen(){
    document.addEventListener("'keydown'", (e: KeyboardEvent) => {
        // prevent snake from backtracking on itself by checking that it's
        // not already moving on the same axis (pressing left while moving
        // left won't do anything, and pressing right while moving left
        // shouldn't let you collide with your own body)

        // left arrow key
        if (e.keyCode === 37 ) {
            this.snake.dx = -this.grid;
            this.snake.dy = 0;
        }
        // up arrow key
        else if (e.keyCode === 38 ) {
            this.snake.dy = -this.grid;
            this.snake.dx = 0;
        }
        // right arrow key
        else if (e.keyCode === 39 ) {
            this.snake.dx = this.grid;
            this.snake.dy = 0;
        }
        // down arrow key
        else if (e.keyCode === 40 ) {
            this.snake.dy = this.grid;
            this.snake.dx = 0;
        }
    });}

       // x(cell, index) {

            // drawing 1 px smaller than the grid creates a grid effect in the snake body so you can see how long it is
         //   this.context.fillRect(cell.x, cell.y, this.grid-1, this.grid-1);
            // snake ate apple
           // if (cell.x === this.apple.x && cell.y === this.apple.y) {
             //  this.snake.maxCells++;
                // canvas is 400x400 which is 25x25 grids
               // this.apple.x = this.getRandomInt(0, 25) * this.grid;
                //this.apple.y = this.getRandomInt(0, 25) * this.grid;
            //}
            // check collision with all cells after this one (modified bubble sort)
      //      for (var i = index + 1; i < this.snake.cells.length; i++) {

                // snake occupies same space as a body part. reset game
        //        if (cell.x === this.snake.cells[i].x && cell.y === this.snake.cells[i].y) {
          //          this.snake.x = 160;
            //        this.snake.y = 160;
              //      this.snake.cells = [];
                //    this.snake.maxCells = 4;
                  //  this.snake.dx = this.grid;
                    //this.snake.dy = 0;
                   // this.apple.x = this.getRandomInt(0, 25) * this.grid;
              //      this.apple.y = this.getRandomInt(0, 25) * this.grid;
               // }

        //}}


    // start the game

    ngOnInit() {
    this.constB();

      //  this.loop();
        requestAnimationFrame(() => this.loop());
       //this.z();
       //this.y();


        if(this.isAuthenticated()){
        }else { this.login();}
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {

        this.modalRef = this.loginModalService.open();
    }
}
