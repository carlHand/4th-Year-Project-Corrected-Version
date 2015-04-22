namespace TrainGainV1.DataContexts.ApplicationMigrations
{
    using Microsoft.AspNet.Identity;
    using Microsoft.AspNet.Identity.EntityFramework;
    using System;
    using System.Collections.Generic;
    using System.Data.Entity;
    using System.Data.Entity.Migrations;
    using System.Drawing;
    using System.IO;
    using System.Linq;
    using TrainGainV1.Models;
    

    internal sealed class Configuration : DbMigrationsConfiguration<TrainGainV1.Models.ApplicationDbContext>
    {
        public Configuration()
        {
            AutomaticMigrationsEnabled = true;
            MigrationsDirectory = @"DataContexts\ApplicationMigrations";
        }
        public byte[] imageToByteArray(System.Drawing.Image imageIn)// System.Drawing.Image imageIn2)//, System.Drawing.Image imageIn3)
        {
            using (var ms = new MemoryStream())
            {
                imageIn.Save(ms, System.Drawing.Imaging.ImageFormat.Png);
                //imageIn2.Save(ms, System.Drawing.Imaging.ImageFormat.Png);
               // imageIn3.Save(ms, System.Drawing.Imaging.ImageFormat.Png);
                return ms.ToArray();
            }
        }

        protected override void Seed(TrainGainV1.Models.ApplicationDbContext context)
        {
            //  This method will be called after migrating to the latest version.

            //  You can use the DbSet<T>.AddOrUpdate() helper extension method 
            //  to avoid creating duplicate seed data. E.g.
            //
            //    context.People.AddOrUpdate(
            //      p => p.FullName,
            //      new Person { FullName = "Andrew Peters" },
            //      new Person { FullName = "Brice Lambson" },
            //      new Person { FullName = "Rowan Miller" }
            //    );
            //

            Image img1 = Properties.Resources.bench_1; //Image.FromFile("bench_1.png");
            Image img2 = Properties.Resources.bench_2;
            Image img3 = Properties.Resources.bench_3;
            byte[] bench1 = imageToByteArray(img1);//, img2);
            byte[] bench2 = imageToByteArray(img2);
            byte[] bench3 = imageToByteArray(img3);

            if (!context.SportContext.Any())
            {
                var Programs = new List<SportProgram>{
                new SportProgram { Sport = "Boxing", Goal="Slipping", Title = "Program for slipping 1", Program="This program is for boxers for Slipping", Genre="Beginner", ApplicationUsers = new List<ApplicationUser>(), Exercises = new List<Exercise>()},
                new SportProgram { Sport = "Boxing", Goal="Slipping", Title = "Program for slipping 2", Program="This program 2 is for boxers for Slipping", Genre="Intermediate", ApplicationUsers = new List<ApplicationUser>()},
                 new SportProgram { Sport = "Boxing", Goal="Slipping", Title = "Program for slipping 3", Program="This program 3 is for boxers for Slipping", Genre="Advanced", ApplicationUsers = new List<ApplicationUser>()},
                new SportProgram { Sport = "Rugby", Goal="Strength", Title = "Pre-season 1 Programmme", Program = "The Lifter performs the exercises in the order listed. \nThe warm-up: should consist of general exercise consisting of jogging or cycling or SAQ drills for 5-10 minutes followed by a general stretching routine.Format: set-repetition or circuit \nNumber of sets or circuits: one set of each exercise for the first six to nine training sessions, two sets thereafter \nResistance: 12 to 15 RM initially \nRest periods: 2 minutes between sets; lifter progresses to 1 ½ minutes \nRepetitions per set for abdominal exercises; 20-30 \nOther: The lifter can choose half of the listed exercises and add one exercise per training session until all the listed exercises are performed. The lifter should increase resistances slowly and in small increments. Stress proper lifting techniques all the time. When adding or replacing exercises, be sure the lifter has learned proper exercise technique before you allow increases in resistance. If an exercise is replaced, make sure that the muscles that it trains are used in other exercises so that proper muscular development is balanced.",
                    DurationInfo = "Two training sessions per week with at least 2 days separating sessions \nThe total time will be between 25 and 55 minutes per session", Genre="Beginner", ApplicationUsers = new List<ApplicationUser>(), Exercises = new List<Exercise>(){
                     new Exercise { Name = "Bench Press", DateLogged = DateTime.Now, ExerciseImageL = new List<Models.ExerciseImage>(){
                        new Models.ExerciseImage { URLimg = "http://i300.photobucket.com/albums/nn39/carlH92/bench_1.png_zpsf63pigyd.jpeg"}, new Models.ExerciseImage { URLimg = "http://i300.photobucket.com/albums/nn39/carlH92/bench_3.png_zpsjupylfi5.jpeg"}, new Models.ExerciseImage { URLimg = "http://i300.photobucket.com/albums/nn39/carlH92/bench_2.png_zpsfo5wvnwe.jpeg"}}
                     ,StepL = new List<Models.Step>(){ new Models.Step { StepDes = "Step 1. Lie on your back on the bench with your feet flat on the floor. Place both hands on the barbell above you at shoulder width. Keep your neck relaxed looking forward to help with breathing during the lift."},
                        new Models.Step { StepDes = "Step 2. Lower the barbell to the middle of your chest in a slow and controlled motion. Continue until the bar is just above your chest. Next, press the barbell straight back above you as you exhale . As you perform this exercise ensure that you remain with your back on the bench."}, 
                        new Models.Step { StepDes = "Step 3. Tips: Place your thumbs around the bar to ensure you have a tight grip. Control the whole movement and prevent the bar from bouncing off your chest as this can cause injury. Lie down on the bench in a position where the bar will not hit off the rack."}}},
                       
                        new Exercise {  Name = "Leg Press", DateLogged = DateTime.Now, ExerciseImageL = new List<Models.ExerciseImage>(){
                        new Models.ExerciseImage { URLimg = "http://i300.photobucket.com/albums/nn39/carlH92/leg_press_one_new_zpscpn4yz0a.png"}, new Models.ExerciseImage{ URLimg = "http://i300.photobucket.com/albums/nn39/carlH92/leg_press_two_new_zpsyv2vwfit.png"}, new Models.ExerciseImage{ URLimg = "http://i300.photobucket.com/albums/nn39/carlH92/leg_press_one_new_zpscpn4yz0a.png"}}
                        , StepL = new List<Models.Step>(){new Models.Step { StepDes = "Step 1. Sit with your back comfortably against the seat and let your hands rest beside your waist. Position your feet on the plaform at equal lengths."}, new Models.Step{StepDes = "Step2. Slowly lower the platform till your legs reach parallel keeping your back firmly against the seat. Do not let your back curve too much."}, 
                            new Models.Step { StepDes = "Step 3. Finally press the platform back up to the starting position with your feet as you exhale fully extending your legs."}}}, 
                        
                        new Exercise {  Name = "Arm Curl", DateLogged = DateTime.Now, ExerciseImageL = new List<Models.ExerciseImage>(){
                        new Models.ExerciseImage { URLimg = "http://i300.photobucket.com/albums/nn39/carlH92/041bd3f6-2675-4440-ab25-60c25e3b173d_zps1cjyki06.jpg"}, new Models.ExerciseImage{ URLimg = "http://i300.photobucket.com/albums/nn39/carlH92/arm_curl_two_zps6m9fitmw.jpg"}, new Models.ExerciseImage{ URLimg = "http://i300.photobucket.com/albums/nn39/carlH92/arm_curl_three_zpskohdmmwa.jpg"}}
                        , StepL = new List<Models.Step>(){new Models.Step { StepDes = "Step 1. Stand or sit with your hands resting comfortably at your sides before you begin with your elbows slightly bent. Keep your back straight"}, new Models.Step{StepDes = "Step2. Slowly begin to raise one arm at a time up to your shoulder as you turn the dumbell. As you raise the dumbell exhale slowly keeping your back straight."},
                            new Models.Step { StepDes = "Step 3. Tip: Do not bend your wrists. Keep your wrists as straight as you can and rest your thumb on top of your index finger."}}}, 
                        
                        new Exercise {  Name = "Knee Curl", DateLogged = DateTime.Now, ExerciseImageL = new List<Models.ExerciseImage>(){
                        new Models.ExerciseImage { URLimg = "http://i300.photobucket.com/albums/nn39/carlH92/knee_curl_one_zpsstmlpwsy.png"}, new Models.ExerciseImage{ URLimg = "http://i300.photobucket.com/albums/nn39/carlH92/knee_curl_two_zps3rzh4vom.png"}}
                        , StepL = new List<Models.Step>(){new Models.Step { StepDes = "Step 1. Lie comfortably on your stomach on the floor with you legs slighty bent and relaxed. Rest one foot on top of the other and place your arms infront of your head for balance."}, new Models.Step{StepDes = "Step2. Slowly raise your legs to a 45 degree angle keeping one foot on top of the other as you exhale out."}}}, 

                        new Exercise {  Name = "Overhead Press", DateLogged = DateTime.Now, ExerciseImageL = new List<Models.ExerciseImage>(){

                        new Models.ExerciseImage { URLimg = "http://s300.photobucket.com/user/carlH92/media/430f05ab-66e8-45dd-8f3f-73b957c9b8d2_zpsq3lcpvuy.jpg.html?sort=3&o=0"}, new Models.ExerciseImage{ URLimg = "http://i300.photobucket.com/albums/nn39/carlH92/b30a0485-4aec-4fec-b9e7-f103d64af574_zpsglsd8ouu.jpg"}, new Models.ExerciseImage{ URLimg = "http://i300.photobucket.com/albums/nn39/carlH92/overhead_press_three_zps5inhkfzj.jpg"}}
                        , StepL = new List<Models.Step>(){new Models.Step { StepDes = "Step 1. Place both dumbells just above your shoulders about 4 inches away from your ears. Keep your arms bent at a 45 degree angle and your back and wrists straight."}, new Models.Step{StepDes = "Step2. Raise both dumbells above your head at the same time remembering not to push your hips forward as this will over extend you back. Stop just before your arms are fully extended."},
                            new Models.Step { StepDes = "Step 3. Alternatively raise one arm at a time over your head again keeping your wrists and back straight."}}}, 

                        new Exercise {  Name = "Knee Extension", DateLogged = DateTime.Now, ExerciseImageL = new List<Models.ExerciseImage>(){
                        new Models.ExerciseImage { URLimg = "http://i300.photobucket.com/albums/nn39/carlH92/knee_extension_one_zpshiur2v7h.jpg"}, new Models.ExerciseImage{ URLimg = "http://i300.photobucket.com/albums/nn39/carlH92/knee_extension_two_zps7bujfbfd.jpg"}}
                        , StepL = new List<Models.Step>(){new Models.Step { StepDes = "Step 1. Sit up straight with your back against the seat and your arms resting at your side. Place your feet under the cushion and point your toes up at an angle."}, new Models.Step{StepDes = "Step2. Raise your legs upwards, stopping just before they reach full extension. As you perform this exerise keep yourself seated and avoid bending your back as you raise your legs."}}}, 
                       
                    }},
                     new SportProgram { Sport = "Basketball", Goal="Dribbling", Title = "Program for Dribbling", Program="This program 1 is for BasketBall", ApplicationUsers = new List<ApplicationUser>(), Exercises = new List<Exercise>()},
                new SportProgram { Sport = "Basketball", Goal="Dribbling", Title = "Program for Dribbling 2", Program="This program 2 is for BasketBall for dribbling", ApplicationUsers = new List<ApplicationUser>(), Exercises = new List<Exercise>()},
                new SportProgram { Sport = "Tennis", Goal="Serve", Title = "Program for Serve", Program="This program is for Tennis", ApplicationUsers = new List<ApplicationUser>(), Exercises = new List<Exercise>()},
                new SportProgram { Sport = "Thaiboxing", Goal="Checking", Title = "Program for checking", Program="This program is for Thaiboxing", ApplicationUsers = new List<ApplicationUser>(), Exercises = new List<Exercise>()},
                new SportProgram { Sport = "Boxing", Goal="Fast Hands", Title = "Program for Fast Hands", Program="This program is for boxers for Fast Hands", ApplicationUsers = new List<ApplicationUser>(), Exercises = new List<Exercise>()},
                new SportProgram { Sport = "Thaiboxing", Goal="Fast Hands", Title = "Program for Fast Hands Thaiboxing", Program="This program is for Thaiboxing for fast hands", ApplicationUsers = new List<ApplicationUser>(), Exercises = new List<Exercise>()},
            };
                Programs.ForEach(s => context.SportContext.AddOrUpdate(p => p.IDsport, s));
                context.SaveChanges();
            }
            var userStore = new UserStore<ApplicationUser>(context);
            var userManager = new UserManager<ApplicationUser>(userStore);
            var userToInsert = new ApplicationUser
            {
                Id = "c98ff0a5-dfcb-4254-b622-4b072e3f36e3",
                UserName = "john",
                Email = "john@hotmail.com",
                Dob = DateTime.Now,
                ShowHelp = true,
                SportProgramCollection = new List<SportProgram>(),
                ExerciseCollection = new List<Exercise>()
            };
            //userManager.Create(userToInsert, "Password-1");
        }
    }
}
