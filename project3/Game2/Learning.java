
class Learning {
  
  Game2 app = null;

  public Learning(Game2 app){
    this.app = app;
  }

  public void hillClimb(Parameterss initialParams, int steps){
    Integer value = 0;
    Parameterss params = initialParams;

    for (int i = 0; i < steps; i++){
        params = optimizeParameters(params);

        Integer newValue = null;
        while(true){
            newValue = foo(params);
            if(newValue != null)
                break;
        }

        if (newValue < value)
            break;

        value = newValue;
        System.out.println("Completed Step: " + i);
    }

    finalParamsPrint(params);

    // Infinite on best 
    Integer runValue = null;
    while(true){
        runValue = foo(params);
        if(runValue != null){
            runValue = null;
            app.reset();
        }
    }
  }

    public Parameterss optimizeParameters(Parameterss initialParams){
        Parameterss runParams = initialParams;
        Integer runValue = null;

        Parameterss bestParams = new Parameterss();
        for(int i = 0; i < runParams.size(); i++){
            bestParams.setTweak(i, initialParams.get(i).val());
        }

        Integer bestRunValue = 0;

        for(int i = 0; i < runParams.size(); i++){
            Parameter p = runParams.get(i);
            int currentParameter = p.val();
            for (int j = 0; j < p.tweaks(); j++){
                if (j>0)
                    p.nextTweak();

                System.out.println("");
                app.reset();
                runParams.print();
                while(true){
                    runValue = foo(runParams);
                    if(runValue != null)
                        break;
                }
                System.out.println("");
                System.out.println("RunValue: " + runValue);
                System.out.println("");

                if (runValue >= bestRunValue){
                    bestRunValue = runValue;
                    bestParams.setTweak(i, p.val());
                    currentParamsPrint(bestParams);
                }
            }
            runParams.setTweak(i, currentParameter);
        }
        bestOptParamsPrint(bestParams, bestRunValue);
        return bestParams;
    }

    public Integer foo(Parameterss params){
        if (!app.currentSpell.isCasting() && app.spellsCast < Game2WorldData.spellCasts){
            int spell = params.getSpellParam(app.spellsCast).val();
            app.ctrl.chooseSpell(spell);
            int target = params.getSpellTargetParam(app.spellsCast).val();
            app.ctrl.selectTarget(target);
            app.ctrl.castOnTarget();
            app.spellsCast += 1;
        }

        if (app.gameOver)
            return app.damageDealt;
        else
            return null;
    }

    public void currentParamsPrint(Parameterss params){
        System.out.println(".........................");
        System.out.println("Current best params");
        params.print();
        System.out.println(".........................");
        System.out.println("");
    }

    public void bestOptParamsPrint(Parameterss params, int value){
        System.out.println("#########################");
        System.out.println("Best of optimize run (" + value + ")");
        params.print();
        System.out.println("#########################");
    }

    public void finalParamsPrint(Parameterss params){
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("Final Params: ");
        params.print();
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$");
    }




}