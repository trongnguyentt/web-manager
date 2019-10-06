import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Mannager } from './mannager.model';
import { MannagerPopupService } from './mannager-popup.service';
import { MannagerService } from './mannager.service';

@Component({
    templateUrl: './mannager-count.component.html'
})
export class MannagerCountComponent implements OnInit {
    private eventManager: JhiEventManager
    mannager: Mannager;


    constructor(
        public activeModal: NgbActiveModal,
        private mannagerService: MannagerService,
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }
    count(){
        this.mannagerService.count().subscribe((mannager) => {
            this.mannager = mannager;
        });
    }
    ngOnInit(): void {
        this.count();
    }





}


@Component({
    template: ''
})
export class MannagerCountPopupComponent implements OnInit {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mannagerPopupService: MannagerPopupService
    ) {}

    ngOnInit() {

                this.mannagerPopupService
                    .open(MannagerCountComponent as Component);
            }

    }



