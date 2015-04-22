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
using TrainGainV1.DataContexts;
using TrainGainV1.Models;

namespace TrainGainV1.Controllers
{
    [RoutePrefix("api/SportPrograms")]
    public class SportProgramsController : ApiController
    {
        private ApplicationDbContext db = new ApplicationDbContext();

        // GET: api/SportPrograms
        public List<SportProgram> GetTrainingPrograms()
        {
            return db.SportContext.ToList();// TrainingPrograms;
        }

        [Route("Search/{Sport}/{Goal}")]
        public IEnumerable<String> GetTrainingProgram(String Sport, String Goal)
        {

            var program = from p in db.SportContext
                          where p.Sport == Sport && p.Goal == Goal 
                          select p.Program;
            return program;
        }

        [Route("Search2/{Sport}/{Goal}")]
        public String GetTrainingProgram2(String Sport, String Goal)
        {
            SportProgram trainingProgram = db.SportContext.FirstOrDefault(w => w.Sport.ToUpper() == Sport.ToUpper() && w.Goal.ToUpper() == w.Goal.ToUpper());
            return trainingProgram.Program;
        }

        [Route("SearchTest/{Sport}/{Goal}")]
        public List<SportProgram> GetTrainingProgram3(String Sport, String Goal)
        {
            var program = db.SportContext.Where(w => (w.Sport.Equals(Sport) && w.Goal.Equals(Goal))).Select(w => w).GroupBy(t => t.Title).Select(d => d.FirstOrDefault());
            return program.ToList();
        }

        [Route("ExerciseImage/{title}")]
        public List<SportProgram> GetTrainingPrograms1(String title)
        {
            var program = db.SportContext.Where(w => (w.Title.Equals(title))).Select(w => w).GroupBy(t => t.Title).Select(d => d.FirstOrDefault());
            return program.ToList();
        }


        // GET: api/SportPrograms/5
        [ResponseType(typeof(SportProgram))]
        public IHttpActionResult GetSportProgram(int id)
        {
            SportProgram sportProgram = db.SportContext.Find(id);
            if (sportProgram == null)
            {
                return NotFound();
            }

            return Ok(sportProgram);
        }

        // PUT: api/SportPrograms/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutSportProgram(int id, SportProgram sportProgram)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != sportProgram.IDsport)
            {
                return BadRequest();
            }

            db.Entry(sportProgram).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!SportProgramExists(id))
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

        // POST: api/SportPrograms
        [ResponseType(typeof(SportProgram))]
        public IHttpActionResult PostSportProgram(SportProgram sportProgram)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.SportContext.Add(sportProgram);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = sportProgram.IDsport }, sportProgram);
        }

        // DELETE: api/SportPrograms/5
        [ResponseType(typeof(SportProgram))]
        public IHttpActionResult DeleteSportProgram(int id)
        {
            SportProgram sportProgram = db.SportContext.Find(id);
            if (sportProgram == null)
            {
                return NotFound();
            }

            db.SportContext.Remove(sportProgram);
            db.SaveChanges();

            return Ok(sportProgram);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool SportProgramExists(int id)
        {
            return db.SportContext.Count(e => e.IDsport == id) > 0;
        }
    }
}