package com.findmeout.android.utils;

import com.findmeout.android.data.MyDatabase;
import com.findmeout.android.data.tables.Categories;
import com.findmeout.android.data.tables.Meanings;
import com.findmeout.android.data.tables.Words;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.ArrayList;

/**
 * Created by umesh0492 on 17/07/16.
 */
public class Utils {

    public static  void insertWordMeaningCategoryResultInDB (ArrayList<Categories> categories) {
        FlowManager.getDatabase (MyDatabase.class)
                .beginTransactionAsync (new ProcessModelTransaction.Builder<> (
                        new ProcessModelTransaction.ProcessModel<Categories> () {
                            @Override
                            public void processModel (Categories category) {
                                // do work here -- i.e. user.delete() or user.update
                                category.save ();
                            }
                        }).addAll (categories).build ())  // add elements (can also handle multiple)
                .error (new Transaction.Error () {
                    @Override
                    public void onError (Transaction transaction, Throwable error) {

                    }
                })
                .success (new Transaction.Success () {
                    @Override
                    public void onSuccess (Transaction transaction) {


                    }
                }).build ().execute ();
    }

    public static  void insertWordMeaningResultInDB (ArrayList<Meanings> meanings) {
        FlowManager.getDatabase (MyDatabase.class)
                .beginTransactionAsync (new ProcessModelTransaction.Builder<> (
                        new ProcessModelTransaction.ProcessModel<Meanings> () {
                            @Override
                            public void processModel (Meanings meaning) {
                                // do work here -- i.e. user.delete() or user.update
                                meaning.save ();
                            }
                        }).addAll (meanings).build ())  // add elements (can also handle multiple)
                .error (new Transaction.Error () {
                    @Override
                    public void onError (Transaction transaction, Throwable error) {

                    }
                })
                .success (new Transaction.Success () {
                    @Override
                    public void onSuccess (Transaction transaction) {

                    }
                })
                .build ().execute ();

    }

    public static void insertWordResultInDB (ArrayList<Words> words) {


        FlowManager.getDatabase (MyDatabase.class)
                .beginTransactionAsync (new ProcessModelTransaction.Builder<> (
                        new ProcessModelTransaction.ProcessModel<Words> () {
                            @Override
                            public void processModel (Words word) {
                                // do work here -- i.e. user.delete() or user.update
                                word.save ();
                            }
                        })
                        .addAll (words).build ())  // add elements (can also handle multiple)
                .error (new Transaction.Error () {
                    @Override
                    public void onError (Transaction transaction, Throwable error) {

                    }
                })
                .success (new Transaction.Success () {
                    @Override
                    public void onSuccess (Transaction transaction) {

                    }
                }).build ().execute ();

    }


}
