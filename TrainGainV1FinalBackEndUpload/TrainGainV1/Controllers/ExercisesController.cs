using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using TrainGainV1.Models;

namespace TrainGainV1.Controllers
{
    [RoutePrefix("api/Exercise")]
    public class ExercisesController : ApiController
    {
        private ApplicationDbContext db = new ApplicationDbContext();

        // GET: api/Exercises
        public List<Exercise> GetExerciseContext()
        {
            return db.ExerciseContext.ToList();
        }

        
        [Route("images/{exercise}")]
        public List<ExerciseImage> GetImages(String exercise)
        {
            var img = db.ExerciseContext.Where(a => a.Name.ToUpper().Equals(exercise.ToUpper())).SelectMany(b => b.ExerciseImageL);
            return img.ToList();
        }

        [Route("CurrExercises/{title}")]
        public List<String> GetCurrExercises(String title)
        {
            var currExer = db.SportContext.Where(a => a.Title.ToUpper().Equals(title.ToUpper())).SelectMany(b => b.Exercises).Select(c=>c.Name);
            return currExer.ToList();
        }
         
        // GET: api/Exercises/5
        [ResponseType(typeof(Exercise))]
        public IHttpActionResult GetExercise(int id)
        {
            Exercise exercise = db.ExerciseContext.Find(id);
            if (exercise == null)
            {
                return NotFound();
            }

            return Ok(exercise);
        }

        // PUT: api/Exercises/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutExercise(int id, Exercise exercise)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != exercise.IDexercise)
            {
                return BadRequest();
            }

            db.Entry(exercise).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ExerciseExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/Exercises
        [ResponseType(typeof(Exercise))]
        public IHttpActionResult PostExercise(Exercise exercise)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.ExerciseContext.Add(exercise);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = exercise.IDexercise }, exercise);
        }

        // DELETE: api/Exercises/5
        [ResponseType(typeof(Exercise))]
        public IHttpActionResult DeleteExercise(int id)
        {
            Exercise exercise = db.ExerciseContext.Find(id);
            if (exercise == null)
            {
                return NotFound();
            }

            db.ExerciseContext.Remove(exercise);
            db.SaveChanges();

            return Ok(exercise);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool ExerciseExists(int id)
        {
            return db.ExerciseContext.Count(e => e.IDexercise == id) > 0;
        }
    }
}